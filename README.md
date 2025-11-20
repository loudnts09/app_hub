# Hub Application

Um aplicativo Android que funciona como um hub central para múltiplos aplicativos utilitários. Desenvolvido em Kotlin com XML layouts e Material Components.

## Sobre o Projeto

O Hub Application é uma aplicação que reúne diversos aplicativos utilitários em um único lugar, facilitando o acesso e a organização de ferramentas do dia a dia.

### Aplicativos Incluídos

1. **CestaScore** - Aplicativo para controle de pontuação de jogos de basquete
2. **Calculadora** - Calculadora com operações matemáticas básicas e avançadas
3. **Notes** - Aplicativo de notas com funcionalidades de criar, visualizar e editar

## Tecnologias Utilizadas

- **Linguagem**: Kotlin
- **UI**: XML Layouts
- **Binding**: ViewBinding (módulo Notes) e findViewById (outros módulos)
- **Design System**: Material Components
- **Build System**: Gradle (Kotlin DSL)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36

### Principais Dependências

- AndroidX Core KTX
- Material Components
- Lifecycle Runtime KTX
- RecyclerView
- AppCompat
- ConstraintLayout
- SQLite (nativo do Android)

## Pré-requisitos

- Android Studio (versão recente)
- JDK 11 ou superior
- Android SDK com API Level 24 ou superior
- Gradle (gerenciado pelo wrapper do projeto)

## Como Executar

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd app_hub
```

2. Abra o projeto no Android Studio

3. Sincronize o projeto (Sync Project with Gradle Files)

4. Execute o aplicativo:
   - Conecte um dispositivo Android ou inicie um emulador
   - Clique em "Run" ou pressione `Shift + F10`

## Estrutura do Projeto

```
app_hub/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/hubapplication/
│   │   │   │   ├── calculator/
│   │   │   │   │   ├── Calculator.kt          # Lógica da calculadora
│   │   │   │   │   └── CalculatorActivity.kt   # Activity da calculadora
│   │   │   │   ├── hoopscore/
│   │   │   │   │   ├── Team.kt                 # Modelo de time
│   │   │   │   │   └── HoopscoreActivity.kt    # Activity do placar
│   │   │   │   ├── notes/
│   │   │   │   │   ├── Note.kt                 # Modelo de nota
│   │   │   │   │   ├── NotesActivity.kt        # Lista de notas
│   │   │   │   │   ├── AddNoteActivity.kt      # Adicionar nota
│   │   │   │   │   ├── UpdateNoteActivity.kt   # Editar nota
│   │   │   │   │   ├── NotesAdapter.kt         # Adapter do RecyclerView
│   │   │   │   │   └── NotesDatabaseHelper.kt   # Helper SQLite
│   │   │   │   ├── ui/theme/                   # Temas (não utilizados)
│   │   │   │   ├── utils/
│   │   │   │   │   ├── Logger.kt                # Sistema de logging
│   │   │   │   │   └── Modules.kt               # Constantes de módulos
│   │   │   │   └── MainActivity.kt             # Activity principal (hub)
│   │   │   ├── res/
│   │   │   │   ├── layout/                     # Layouts XML
│   │   │   │   ├── values/                     # Strings, cores, temas
│   │   │   │   └── drawable/                   # Ícones e backgrounds
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                        # Testes de instrumentação
│   │   └── test/                               # Testes unitários
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml                      # Versões das dependências
├── build.gradle.kts
└── settings.gradle.kts
```

## Funcionalidades

### MainActivity
- Tela inicial que serve como hub central
- Navegação para os diferentes aplicativos
- Interface com Material Components e cards

### CestaScore (Hoopscore)
- Controle de pontuação para jogos de basquete
- Dois times (Time A e Time B)
- Pontuação: 1 ponto (tiro livre), 2 pontos, 3 pontos
- Sistema de streak (sequência de pontuações) com indicador visual
- Edição de nomes dos times
- Reiniciar partida

### Calculadora
- Operações básicas: adição, subtração, multiplicação, divisão
- Operações avançadas: raiz quadrada, logaritmo base 10, porcentagem
- Constantes: π (pi)
- Histórico de operações
- Suporte a números negativos
- Backspace e clear
- Persistência de estado ao rotacionar tela

### Notes
- Criar novas notas (título e conteúdo)
- Visualizar lista de notas em RecyclerView
- Editar notas existentes
- Armazenamento local com SQLite
- Banco de dados: `notesapp.db` com tabela `allnotes`

## Desenvolvimento

### Adicionando Novos Módulos

Para adicionar um novo aplicativo ao hub:

1. Crie uma nova Activity no pacote apropriado
2. Crie o layout XML correspondente em `res/layout/`
3. Adicione a Activity no `AndroidManifest.xml` com `parentActivityName` apontando para `MainActivity`
4. Adicione um botão/card na `MainActivity` (layout `activity_main.xml`) para navegar até a nova Activity
5. Configure o `setOnClickListener` na `MainActivity.kt`
6. Adicione o módulo em `utils/Modules.kt` se necessário para logging

### Logging

O projeto utiliza um sistema de logging customizado através da classe `Logger` em `utils/Logger.kt`. Os módulos são definidos em `utils/Modules.kt`.
