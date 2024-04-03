const express = require("express");
const sqlite3 = require("sqlite3").verbose();

const app = express();
const PORT = process.env.PORT || 3000;

var bodyParser = require("body-parser");
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Criar uma conexão com o banco de dados SQLite persistente em um arquivo
const db = new sqlite3.Database("banco_de_dados.db");

// Criar a tabela 'usuarios' no banco de dados, se ainda não existir
db.serialize(() => {
  db.run(
    "CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)",
  );
});

// Middleware para processar solicitações JSON
app.use(express.json());

// Rota para receber dados do aplicativo Android e salvá-los no banco de dados
app.post("/cadastrar", (req, res) => {
  //const { username, password } = req.body;

  // Inserir os dados recebidos no banco de dados
  db.all(
    `INSERT INTO usuarios (username, password) VALUES ('${req.body.username}', '${req.body.password}')`,
    (err) => {
      if (err) {
        console.error("Erro ao inserir dados:", err.message);
        return res.status(500).send("Erro ao cadastrar usuário");
      }
      console.log("Usuário cadastrado com sucesso");
      res.status(200).send("Usuário cadastrado com sucesso");
    },
  );
});

// Rota para buscar todos os usuários do banco de dados
app.get("/usuarios", (req, res) => {
  // Consultar o banco de dados para buscar todos os usuários
  db.all("SELECT * FROM usuarios", (err, rows) => {
    if (err) {
      console.error("Erro ao buscar usuários:", err.message);
      res.status(500).send("Erro ao buscar usuários");
    } else {
      // Enviar os dados dos usuários como resposta
      res.status(200).json(rows);
    }
  });
});

// Iniciar o servidor
app.listen(PORT, () => {
  console.log(`Servidor iniciado na porta ${PORT}`);
});

