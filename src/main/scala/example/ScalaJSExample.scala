package example
import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Document

import scala.scalajs.js
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import dom.ext._
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue

@JSExport
object ScalaJSExample extends {
  @JSExport
  def main(): Unit = {
    appendPar(document.body, "Bobby Test")
    interactiveFilters(document.body)
    jsonPosts(document.body)
  }

  def jsonPosts(targetNode: dom.Node) = {
    val parNode = document.createElement("div")

    val url = "http://jsonplaceholder.typicode.com/posts/1"

    Ajax.get(url).onSuccess {
      case xhr =>
        val json = js.JSON.parse(
          xhr.responseText
        )
        val id = json.id.toString
        val userid = json.userId.toString
        val title = json.title.toString
        parNode.appendChild(
          div(
            b("Parsing API Call:"),
            ul(
              li(b("id "), id),
              li(b("userid "), userid),
              li(b("title "), title.substring(1, 10))
            )
          ).render
        )
        targetNode.appendChild(parNode)
    }
  }

  def interactiveFilters(targetNode: dom.Node) = {
    val parNode = document.createElement("div")
    val box = input(
      `type` := "text",
      placeholder := "Type here!"
    ).render

    val listings = Seq(
      "Apple",
      "Apricot",
      "Banana",
      "Cherry",
      "Mango",
      "Mangosteen",
      "Mandarin",
      "Grape",
      "Grapefruit",
      "Guava"
    )

    def renderListings =
      ul(
        for {
          fruit <- listings
          if fruit.toLowerCase.startsWith(
            box.value.toLowerCase
          )
        } yield li(fruit)
      ).render

    val output = div(renderListings).render

    box.onkeyup = (e: dom.Event) => {
      output.innerHTML = ""
      output.appendChild(renderListings)
    }

    parNode.appendChild(
      div(
        h1("Search Box!"),
        p(
          "Type here to filter " +
            "the list of things below!"
        ),
        div(box),
        output
      ).render
    )
    targetNode.appendChild(parNode)
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("div")
    val box = input(
      `type` := "text",
      placeholder := "Type here!"
    ).render

    val output = span.render

    box.onkeyup = (e: dom.Event) => {
      output.textContent = box.value.toUpperCase
    }

    parNode.appendChild(
      div(
        h1("Capital Box!"),
        p(
          "Type here and " +
            "have it capitalized!"
        ),
        div(box),
        div(output)
      ).render
    )
    targetNode.appendChild(parNode)

  }
}
