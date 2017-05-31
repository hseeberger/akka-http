/*
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
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
 * Mixing in this trait lets a source of [[ServerSentEvent]]s be marshalled to a `HttpEntity` and hence as a
 * `HttpResponse`.
 */
trait EventStreamMarshalling {

  implicit final val toEventStream: ToEntityMarshaller[Source[ServerSentEvent, Any]] = {
    def marshal(messages: Source[ServerSentEvent, Any]) = HttpEntity(`text/event-stream`, messages.map(_.encode))
    Marshaller.withFixedContentType(`text/event-stream`)(marshal)
  }
}
