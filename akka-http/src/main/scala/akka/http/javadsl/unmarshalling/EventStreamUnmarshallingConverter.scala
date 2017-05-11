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
package javadsl
package unmarshalling

import akka.NotUsed
import akka.http.javadsl.model.HttpEntity
import akka.stream.javadsl.Source
import akka.http.javadsl.model.ServerSentEvent

private object EventStreamUnmarshallingConverter {

  final val fromEventStream: Unmarshaller[HttpEntity, Source[ServerSentEvent, NotUsed]] =
    scaladsl.unmarshalling.EventStreamUnmarshalling.fromEventStream
      .map(_.map(_.asInstanceOf[ServerSentEvent]).asJava)
      .asInstanceOf[Unmarshaller[HttpEntity, Source[ServerSentEvent, NotUsed]]]
}
