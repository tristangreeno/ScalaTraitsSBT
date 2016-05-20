import org.scalatest.{FeatureSpec, GivenWhenThen}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Lists the features the user desires for the application to employ.
  */

class CorporationSpec extends FeatureSpec with GivenWhenThen {
  feature("Authentication system") {
    info("As an administrator, ")
    info("I want each user to log in")
    info("If their username does not exist")
    info("Then they should be able to make a new account")
    info("If their credentials are invalid, ")
    info("Then do not give the user access")

    scenario("User makes a new account") {
      Given("the user has a valid key given to him from the company")
      val key = "123"
      val username = "user"
      val password = "password"

      /* def checkKey(key: String): Boolean = {
        server.key.equals(key)
      } */

      When("the user does not have an account")
      And("enters his key")
      assert(Server.checkKey(key))

      Then("allow user to make an account")
      assert(Server.login(username, password))
    }

    scenario("User logs in with incorrect credentials") {
      Given("the user has entered username")
      val username = "username"
      And("the user already exists")
      Server.addUser("username", "password", "123")

      When("the password does not match the one in the list of users")
      val password = "bad"

      Then("do not allow the user to log in")

      assert(! Server.login(username, password))
    }

    scenario("User logs in with correct credentials") {
      Given("the user has entered username and password")
      Server.addUser("username", "password", "123")
      val username: String = "username"
      val password: String = "password"
      assert(Server.login(username, password))

      /*
        NOTE: Difference between Scala and Java
       .get method on HashMap returns whether a key contains a value (Some or None)
       .apply method = Java .get ---> IMPORTANT
        */

      When("the user has logged in successfully with valid credentials")
      assert(Server.checkPassword("username", "password"))


      Then("prompt the user to enter a title to find a list of users")
      val title = Server.prompt("Enter a title to find list of employees with that title:")
      assert(title != null)
    }
  }

  feature("Display a list of employees, according to title") {
    info("As a hiring manager, ")
    info("I want to see a list of employees ")
    info("So I can see their identification and title, ")
    info("See only people that hold a particular title, ")
    info("And know how many persons holds a given title.")

    scenario("User enters a title to filter by") {
      Given("there is a list of employees")
      And("the user has logged in successfully")

      var employees = new ListBuffer[Employee]
      employees += new Employee("id", "Mgr")
      employees += new Employee("ed", "Wrk")
      val employeesList = employees
      assert(employeesList.nonEmpty)

      When("the user enters a title")
      val filter: String = Server.prompt("Please enter a title to filter [Wkr (Worker) or Mgr (Manager)]:")

      Then("display a list of employees with that title, and their respective ID")
      val hashMap = new mutable.HashMap[String, String]
      employeesList.foreach(employee => {
        hashMap.put(employee.id, employee.toString)
      })

      var returnedEmployees = new ListBuffer[Employee]

      hashMap.filter(map => map._2.contains(s"$filter"))
        .foreach(map => {
          returnedEmployees += new Employee(map._1, map._2)
        })

      assert(returnedEmployees.nonEmpty)
    }
  }
}
