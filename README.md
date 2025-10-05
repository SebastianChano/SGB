# 📚 SGB - Sistema de Gestão de Biblioteca

<div align="center">

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Swing](https://img.shields.io/badge/Java%20Swing-GUI%20Desktop-yellow?style=for-the-badge)

<img src="https://images.joseartgallery.com/73141/conversions/portrait-painting-van-gogh-portrait-thumb1920.jpg" alt="Van Gogh" width="450"/>

</div>

---

> _“Eu sonho minha pintura e depois pinto meu sonho.”_  
> — **Vincent Van Gogh**

---

SGB é um sistema de gestão bibliotecária completo, com interface artística inspirada em Van Gogh.  
**Tecnologia, arte e usabilidade.**

---

## 🎨 Sobre o Projeto

O **SGB - Sistema de Gestão de Biblioteca** é uma aplicação desktop escrita em Java, idealizada para unir robustez, eficiência e uma experiência visual única. Inspirado nas obras impressionistas de Vincent Van Gogh, cada detalhe do sistema reflete criatividade, cor e emoção.

---

## ✨ Inspiração Van Gogh

---

## 🚀 Funcionalidades Principais

### 📖 Módulo de Livros

- **Busca Avançada:** Filtros por código, centro, categoria e descrição
- **Controle de Estoque:** Gerenciamento de quantidades mínimas e máximas
- **Tipos de Movimento:** Controle preciso de entradas e saídas
- **Interface Intuitiva:** Tabelas dinâmicas, rápidas e responsivas

### 📊 Módulo de Estatísticas

- **Gráficos Interativos:** Linhas, barras, pizza e horizontais
- **Dashboard Completo:** Visão clara de vendas, cadastros e produtos
- **Atualização em Tempo Real:** Dados sempre sincronizados

### 👥 Módulo de Cadastro

- **Cadastro Duplo:** Clientes e Funcionários
- **Validação de CPF e Email:** Em tempo real

### 🛒 Módulo de Pedidos

- **Autenticação Segura:** Login com CPF/Email e senha
- **Busca Rápida:** Autocompletar por código do livro
- **Cálculo Automático:** Quantidade sugerida inteligente
- **Fluxo Simplificado:** Processo de pedido ágil e visual

---

## 🏗️ Estrutura do Projeto

```
SGB/
├── Conexao/
│   └── ConexaoDB.java
├── SGB/
│   ├── Main.java
│   ├── Telas/
│   │   ├── TelaCadastro.java
│   │   ├── TelaEstatisticas.java
│   │   ├── TelaLivros.java
│   │   ├── TelaLogin.java
│   │   ├── TelaMenu.java
│   │   ├── TelaPedidos.java
│   │   └── TelaSplash.java
│   ├── Telas.graficos/
│   │   ├── DadosGrafico.java
│   │   ├── GraficoBarras.java
│   │   ├── GraficoBarrasHorizontal.java
│   │   ├── GraficoLinha.java
│   │   └── GraficoPizza.java
│   ├── Telas.imagens/
│   │   ├── cadastro.png
│   │   ├── estatisticas.png
│   │   ├── livros.png
│   │   ├── pedidos.png
│   │   └── sair.png
│   ├── dao/
│   │   ├── EstatisticasDAO.java
│   │   ├── LivroDAO.java
│   │   ├── PedidoDAO.java
│   │   └── UsuarioDAO.java
│   └── model/
│       ├── Livro.java
│       ├── Pedido.java
│       └── Usuario.java
├── Test Packages/
└── Libraries/
│       ├── mysql-connector-j-9.4.0.jar
│       └── JDK 21 (Default)   
└── Test Libreries/
```

---

## 🎯 Arquitetura e Padrões

- **DAO:** Abstração do acesso ao banco
- **MVC:** Separação clara de responsabilidades
- **Singleton:** Conexão única ao BD
- **Factory Method:** Criação de componentes gráficos

**Camadas:**
- _Apresentação (Telas)_
- _Negócio (DAO)_
- _Dados (Model)_
- _Persistência (Conexao)_

---

## 🔧 Tecnologias Utilizadas

- **Java SE 17+**
- **Java Swing**
- **MySQL 8.0**
- **JDBC**
- **mysql-connector-java-8.0.xx.jar**
- *JDK 21 (Default)*

---

## 🚀 Como Executar

### Pré-requisitos

- Java JDK 17 ou superior
- MySQL Server 8.0+
- NetBeans IDE (ou sua favorita)

### Configuração do Banco

```sql
CREATE DATABASE sgb_biblioteca;
USE sgb_biblioteca;
-- Execute o script SQL fornecido para criar as tabelas
```

No arquivo `Conexao/ConexaoDB.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sgb_biblioteca";
private static final String USUARIO = "seu_usuario";
private static final String SENHA = "sua_senha";
```

### Execução

#### No NetBeans

- Abra o projeto
- Configure o classpath com o conector MySQL
- Execute `SGB/Main.java`

#### Linha de Comando

```bash
# Compilar
javac -cp ".;mysql-connector-java-8.0.xx.jar" SGB/*.java SGB/Telas/*.java SGB/dao/*.java SGB/model/*.java Conexao/*.java
# Executar
java -cp ".;mysql-connector-java-8.0.xx.jar" SGB.Main
```

---

## 📋 Fluxo da Aplicação

1. **🎬 TelaSplash** → Apresentação artística
2. **🔐 TelaLogin** → Autenticação
3. **🏠 TelaMenu** → Navegação central
4. **📊/📚/🛒/👥** → Módulos específicos

---

## 🔍 Módulos Detalhados

### 🔐 TelaLogin

- Autenticação com CPF/email e senha
- Validação em tempo real
- Recuperação de senha assistida

### 🏠 TelaMenu

- Ícones customizados e navegação visual
- Informações contextuais do usuário

### 📚 TelaLivros

- Busca e filtros múltiplos
- Controle de estoque

### 📊 TelaEstatisticas

- Gráficos dinâmicos e interativos
- Dados sincronizados em tempo real

### 👥 TelaCadastro

- Cadastro de clientes/funcionários
- Validação e verificação de duplicatas

### 🛒 TelaPedidos

- Processo de pedidos guiado, integrado e inteligente

---

## 🛡️ Segurança & Validações

- **Validação de credenciais no servidor**
- **Controle de acesso por tipo de usuário**
- **Proteção contra SQL Injection**
- **Mascaramento de campos sensíveis**
- **CPF e Email válidos e únicos**
- **Senha forte (mín. 8 caracteres)**

---

## 🤝 Contribua!

1. 🍴 Faça um fork
2. 🌿 Crie uma branch (`git checkout -b feature/NomeDaFeature`)
3. 💾 Commit (`git commit -m 'Add feature'`)
4. 📤 Push (`git push origin feature/NomeDaFeature`)
5. 🔄 Abra um Pull Request

---

## 📄 Licença

Projeto sob licença MIT. Veja o arquivo [LICENSE](LICENSE).

---

<div align="center">

👨‍💻 Desenvolvido com ❤️ e ☕  
_"Grandes coisas não são feitas por impulso, mas por uma série de pequenas coisas reunidas."_  
— Vincent Van Gogh

⭐ **Se este projeto te inspirou, deixe sua estrela!** ⭐

Que a beleza da arte e a eficiência da tecnologia caminhem juntas.  
🎨 Inspirado nas obras de Vincent Van Gogh

</div>
