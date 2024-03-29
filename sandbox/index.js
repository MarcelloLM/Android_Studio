var express = require("express");
var app = express();
var port = process.env.PORT || 3000;
app.get("/", function (req, res) {
  res.send("Pagina de teste da aula de node");
});
app.get("/sobre", function (req, res) {
  res.send("Segundo teste da aula de node");
});
app.listen(port);
