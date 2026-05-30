# RetroVault

RetroVault is an Android app for organizing a retro game library (academic demo-ready).

## Objetivo
Permitir organização completa da coleção retrô: favoritos, lista de desejos, jogos zerados e estatísticas.

## Tecnologias
- Kotlin
- Jetpack Compose
- Material Design 3
- Navigation Compose
- Room (SQLite)
- Coroutines / StateFlow
- MVVM + Repository Pattern

## Estrutura principal
- app/src/main/java/.../data/local — Room entities, DAO, Database
- app/src/main/java/.../data/repository — Implementação do repositório
- app/src/main/java/.../domain/model — Modelos de domínio
- app/src/main/java/.../presentation — UI, ViewModels e navegação

## Funcionalidades implementadas
- CRUD de jogos com Room
- Favoritos, Lista de Desejos, Marcação de jogos zerados
- Estatísticas básicas (total, favoritos, zerados, desejados, média de notas)
- Telas: Home, Detalhes, Adicionar/Editar, Dashboard (placeholder), Perfil (placeholder), Configurações (placeholder)
- Temas e melhorias visuais planejadas

## Executando
1. Abra o projeto no Android Studio
2. Execute em terminal (na raiz do projeto):

```powershell
.
\gradlew.bat assembleDebug
```

## Observações
- Foi adicionada migração de banco (versão 2 → 3) para incluir novos campos. Testar em ambiente de desenvolvimento.
- Algumas telas (Dashboard/Profile/Settings) são esqueleto e podem ser enriquecidas com estatísticas e preferências persistentes.

## Capturas de tela
(Adicione imagens em /docs ou no README quando disponíveis)

## Links
- Figma: (espaço reservado)
- Vídeo demonstrativo: (espaço reservado)

