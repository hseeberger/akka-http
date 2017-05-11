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
package marshalling

import akka.http.scaladsl.model.HttpEntity
import akka.stream.scaladsl.Source
import akka.http.scaladsl.model.MediaTypes.`text/event-stream`
import akka.http.scaladsl.model.ServerSentEvent

/**
 * Importing [[EventStreamMarshalling.toEventStream]] lets a source of [[ServerSentEvent]]s be marshalled to a
 * `HttpEntity` and hence as a `HttpResponse`.
 */
object EventStreamMarshalling extends EventStreamMarshalling

/**
 * Mixing in this trait lets a source of [[ServerSentEvent]]s be marshalled to a a `HttpEntity` and hence as a
 * `HttpResponse`.
 */
trait EventStreamMarshalling {

  implicit final val toEventStream: ToEntityMarshaller[Source[ServerSentEvent, Any]] = {
    def marshal(messages: Source[ServerSentEvent, Any]) = HttpEntity(`text/event-stream`, messages.map(_.encode))
    Marshaller.withFixedContentType(`text/event-stream`)(marshal)
  }
}
