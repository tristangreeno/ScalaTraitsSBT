import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * Holds the crucial functions of the application, such as the login system and information about each user.
  */
object Server {
  val key = "123"
  val userList = mutable.MutableList[User] ()
  /*val users = new mutable.HashMap[String, String]
  userList.foreach(u => {
    users.put(u.username, u.password)
  })*/

  def addUser(username: String, password: String, key: String): Boolean = {

    if(checkKey(key)){
      userList += new User(username, password)
      true
    }
    else {
      println("Invalid key.")
      false
    }
  }

  def login(username: String, password: String): Boolean = {
    if(checkUsername(username, password)) true
    else {
      println("Sorry, we could not log you in.")
      false
    }
  }

  def checkUsername(username: String, password: String): Boolean = {
    if(userList.exists(u => u.username.equals(username))) checkPassword(username, password)
    else addUser(username, password, prompt("Enter your authentication key:"))
  }

  def checkPassword(username: String, password: String): Boolean = {
    userList.filter(u => u.username.equals(username))
      .last.password.equals(password)
  }

  def prompt(s: String): String = {
    println(s)
    io.StdIn.readLine()
  }

  def checkKey(key: String): Boolean = {
   this.key.equals(key)
  }

  def showListOfEmployees() = {
    var employees = new ListBuffer[Employee]
    employees += new Employee("id", "Mgr")
    employees += new Employee("ed", "Wrk")
    val employeesList = employees
    val hashMap = new mutable.HashMap[String, String]

    employeesList.foreach(employee => {
      hashMap.put(employee.id, employee.toString)
    })

    val filter: String = Server.prompt("Please enter a title to filter [Wkr (Worker) or Mgr (Manager)]:")

    var returnedEmployees = new ListBuffer[Employee]

    hashMap.filter(map => map._2.contains(s"$filter"))
      .foreach(map => {
        returnedEmployees += new Employee(map._1, map._2)
        println(s"${map._2.toString}")
      })
  }
}
