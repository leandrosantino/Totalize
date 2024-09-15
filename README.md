# Totalize

## Padrão de Commits

```
type(scope): !subject <br>
<!body> <br>
```

- **Type (tipo)**:
    * O tipo de modificação que foi realizada no projeto
- **Scope (escopo)**:
    * A região (pasta, arquivo, ou feature) do projeto onde foi realizada a modificação. <br>
    *Obs: Para mudanças que afetam todo o projeto não é necessário informar o escopo.*
- **Subject (asunto)**:
    * Uma descrição curta da modificação realizada.
- **Body (corpo)** {opcional}:
    * Uma descrição detalhada da modificação realizada

### Tipo de Commit do projeto:
- `feat` : indica o desenvolvimento de uma nova funcionalidade ao projeto. <br>
**Exemplo**: Acréscimo de um serviço, funcionalidade, endpoint, etc.

- `refactor`: usado quando houver uma refatoração de código que não tenha qualquer tipo de impacto na lógica/regras de negócio do sistema. <br>
**Exemplo**: Mudanças de código após um code review

- `fix`: utilizado quando há correção de erros que estão gerando bugs no sistema. <br>
**Exemplo**: Aplicar tratativa para uma função que não está tendo o comportamento esperado e retornando erro.

- `style`: empregado quando há mudanças de formatação e estilo do código que não alteram o sistema de nenhuma forma. <br>
**Exemplo**: Arrumar indentações, remover espaços em brancos, remover comentários, etc..

- `chore`: indica mudanças no projeto que não afetem o sistema ou arquivos de testes. São mudanças de desenvolvimento. <br>
**Exemplo**: Mudar regras do eslint, adicionar prettier, adicionar mais extensões de arquivos ao .gitignore, modificar estrutura de pastas.
