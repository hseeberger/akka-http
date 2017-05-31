/*
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.http
package javadsl
package marshalling
package sse

import akka.http.javadsl.model.RequestEntity
import akka.http.javadsl.model.sse.ServerSentEvent
import akka.stream.javadsl.Source

private object EventStreamMarshallingConverter {

  final def toEventStream[A]: Marshaller[Source[ServerSentEvent, A], RequestEntity] = {
    def asScala(eventStream: Source[ServerSentEvent, A]) =
      eventStream.asScala.map(_.asInstanceOf[scaladsl.model.sse.ServerSentEvent])
    Marshaller.fromScala(scaladsl.marshalling.sse.EventStreamMarshalling.toEventStream.compose(asScala))
  }
}
