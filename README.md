# Teste Técnico Android - Starta + Sicredi

Solução desenvolvida para o teste técnico apresentado.

## Decisões

- Interface desenvolvida majoritariamente usando Compose UI (Jetpack Compose), pela velocidade de desenvolvimento
- OkHttp usado como framework de websocket, para realizar as requisições à API
- O framework ThreeTen foi escolhido para possibilitar o uso dos tipos LocalDateTime, e afins, em versões anteriores à API 26

## Testes

Devido a pouca experiência com o desenvolvimento de testes (apenas conhecimento teórico da época de faculdade), não foram descritos testes.

## Firebase

Foi deixado espaço para configurar o Firebase para monitorar qualidade e falhas, mas para agilizar, tal configuração não foi realizada

## Arquitetura

Configurado para ser reativo aos retornos da API. Sem guardar o estado localmente, por simplicidade.
