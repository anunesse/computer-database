package com.excilys.formation.projet.controller.wrapper;

public class Error {
String type;
String title;
String message;

public Error(String type, String title, String message) {
super();
this.type = type;
this.title = title;
this.message = message;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}
}
