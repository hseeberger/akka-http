/*
 * Copyright (C) 2009-2017 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.http
package scaladsl
package unmarshalling

import akka.NotUsed
import akka.stream.scaladsl.Flow
import akka.util.ByteString
import akka.http.scaladsl.model.ServerSentEvent

/**
 * Flow that converts raw byte string input into [[ServerSentEvent]]s.
 *
 * This API is made for use in non-akka-http clients, like Play's WSClient.
 */
object EventStreamParser {

  /**
   * Flow that converts raw byte string input into [[ServerSentEvent]]s.
   *
   * This API is made for use in non-akka-http clients, like Play's WSClient.
   *
   * @param maxLineSize The maximum size of a line for the event Stream parser
   * @param maxEventSize The maximum size of a server-sent event for the event Stream parser
   */
  def apply(maxLineSize: Int, maxEventSize: Int): Flow[ByteString, ServerSentEvent, NotUsed] =
    Flow[ByteString].via(new LineParser(maxLineSize)).via(new ServerSentEventParser(maxEventSize))
}
