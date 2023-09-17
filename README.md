# Agenda BBDD

Agenda BBDD is a simple Java application that demonstrates a Model-View-Controller (MVC) architecture for managing notes in a MySQL database. This application allows you to view, create, modify, and delete notes.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Screenshots](#screenshots)

## Features

- View a list of notes from a MySQL database.
- Create new notes with a date and content.
- Modify existing notes.
- Delete notes.
- User-friendly graphical interface.

## Prerequisites

To run this application, you need to have the following installed:

- Java Development Kit (JDK)
- MySQL Database

## Installation

1. Clone this repository to your local machine:

```bash
git clone https://github.com/miguelrodriguez19/AgendaBBDD.git
```

Import the project into your favorite Java IDE (e.g., Eclipse, IntelliJ IDEA).

Configure your MySQL database settings in the Modelo.java file:

```java
private String bd = "agendaBBDD";
private String login = "root";
private String pwd = "";
private String url = "jdbc:mysql://localhost/" + bd;
```
Make sure to replace "root" and "" with your MySQL username and password, if necessary.

Run the Main.java class to start the application.
## Usage
Launch the application, and you will see the main interface.
Click on the "Mostrar" button to load notes from the MySQL database.
Select a note from the table to edit or delete it.
Fill in the date and content fields and click "Nueva" to create a new note.
## Screenshots
| Home | Table | Example |
|---------|-------------|------|
| ![Home](/Screenshots/home.jpg) | ![Table](/Screenshots/table.jpg) | ![Example](/Screenshots/example.jpg) |
