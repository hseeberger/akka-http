/*
 * Copyright 2015 Heiko Seeberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package akka.http
package scaladsl
package unmarshalling

import akka.NotUsed
import akka.http.scaladsl.model.HttpEntity
import akka.stream.scaladsl.Source
import akka.http.scaladsl.model.MediaTypes.`text/event-stream`
import akka.http.scaladsl.model.ServerSentEvent

/**
 * Importing [[EventStreamUnmarshalling.fromEventStream]] lets a `HttpEntity` with a `text/event-stream` media type be
 * unmarshalled to a source of [[ServerSentEvent]]s.
 *
 * The maximum size for parsing server-sent events is 8KiB. The maximum size for parsing lines of a server-sent event
 * is 4KiB. If you need to customize any of these, use the [[EventStreamUnmarshalling]] trait and override the
 * respective methods.
 */
object EventStreamUnmarshalling extends EventStreamUnmarshalling

/**
 * Mixing in this trait lets a `HttpEntity` with a `text/event-stream` media type be unmarshalled to a source of
 * [[ServerSentEvent]]s.
 *
 * The maximum size for parsing server-sent events is 8KiB by default and can be customized by overriding
 * [[EventStreamUnmarshalling.maxEventSize]]. The maximum size for parsing lines of a server-sent event is 4KiB dy
 * default and can be customized by overriding [[EventStreamUnmarshalling.maxLineSize]].
 */
trait EventStreamUnmarshalling {

  /**
   * The maximum size for parsing server-sent events; 8KiB by default.
   */
  protected def maxEventSize: Int =
    8192

  /**
   * The maximum size for parsing lines of a server-sent event; 4KiB by default.
   */
  protected def maxLineSize: Int =
    4096

  implicit final val fromEventStream: FromEntityUnmarshaller[Source[ServerSentEvent, NotUsed]] = {
    val lineParser = new LineParser(maxLineSize)
    val eventParser = new ServerSentEventParser(maxEventSize)
    def unmarshal(entity: HttpEntity) =
      entity
        .withoutSizeLimit // Because of streaming: the server keeps the response open and potentially streams huge amounts of data
        .dataBytes
        .via(lineParser)
        .via(eventParser)
        .mapMaterializedValue(_ ⇒ NotUsed: NotUsed)
    Unmarshaller.strict(unmarshal).forContentTypes(`text/event-stream`)
  }
}
