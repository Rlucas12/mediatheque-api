package core

package object models {

  /**
    * Type used to map sql ID
    */
  type UUID = java.util.UUID

  /**
    * Literally, Nothing at all
    * Use Nix in place of [[Unit]] when you return a Tx or a Future without result.
    * Why it's forbidden to use Unit? `Future(Future(Hey)): Future[Unit]` is accepted by the compiler because Unit is an ancestor of all types
    */
  case object Nix
  type Nix = Nix.type // Magic!

}