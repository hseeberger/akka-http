/*
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
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
