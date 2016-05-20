object App {
  def main(args: Array[String]) {
    val username: String = Server.prompt("Enter your username:")
    val password: String = Server.prompt("Enter your password:")

    if(Server.login(username, password)) Server.showListOfEmployees()
    else println("Access denied.")


  }
}
