import org.scalatest.FlatSpec

/**
  * Tests the functions inside of the Corporation object.
  */

class CorporationTest extends FlatSpec {
  "A user" should "have a username and password" in {
    val u = new User("username", "password")
    assert(u.username.equals("username"))
  }
}
