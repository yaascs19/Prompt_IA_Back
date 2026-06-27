# EcoLivros Backend

API REST em Java Spring Boot com autenticação JWT e PostgreSQL.

## Endpoints

### Auth (público)
- `POST /api/auth/register` — Cadastro
- `POST /api/auth/login` — Login

### Books
- `GET /api/books` — Listar todos (público)
- `GET /api/books?search=termo` — Pesquisar (público)
- `GET /api/books/{id}` — Detalhes (público)
- `GET /api/books/my` — Meus livros (autenticado)
- `POST /api/books` — Cadastrar livro (autenticado)
- `DELETE /api/books/{id}` — Deletar livro (autenticado)

## Variáveis de Ambiente

```
DATABASE_URL=jdbc:postgresql://<host>:<port>/<database>
DATABASE_USERNAME=<usuario>
DATABASE_PASSWORD=<senha>
JWT_SECRET=<string-aleatoria-minimo-32-caracteres>
```

## Deploy no Render

1. Crie um banco **PostgreSQL** no Render e copie as credenciais.
2. Crie um serviço **Web Service** apontando para este repositório.
3. Selecione **Docker** como runtime.
4. Adicione as variáveis de ambiente acima nas configurações do serviço.
5. O Render vai buildar e subir automaticamente.

## Executar localmente

Crie um arquivo `.env` ou exporte as variáveis e rode:

```bash
mvn spring-boot:run
```
