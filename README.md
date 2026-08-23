
1. # **Documento de Especificação de Requisitos**

**Projeto:** Triagem Inteligente de Currículos **Data de Criação:** 19/08/2026 

**Autor:** Carlos Henrique Bispo dos Santos Júnior

Conteúdo

1. Introdução 1.1 Objetivo 1.2 Escopo do produto 1.3 Definições, Acrônimos e Abreviações

2. Descrição geral 2.1 Modo de Operação 2.2 Funções do produto 2.3 Usuários e sistemas externos 2.4 Restrições 2.5 Requisitos Adiados

3. Requisitos específicos 3.1 Requisitos Funcionais 3.2 Requisitos Não-Funcionais 3.2.1 Desempenho 3.2.2 Usabilidade 3.2.3 Segurança 3.2.4 Expansibilidade 3.3 Requisitos de Interface 3.3.1 Interfaces de Usuário 3.3.2 Interfaces de Hardware 3.3.3 Interfaces de Software 3.3.4 Interfaces de Comunicação

4. Restrições e Limitações

5. Glossário

6. Referências

7. Aprovação

   1. ## **1 Introdução**

A Triagem Inteligente de Currículos tem como finalidade auxiliar empresas e profissionais de Recursos Humanos na organização e análise de currículos durante processos seletivos. O sistema permite que o candidato se cadastre, envie seu currículo e se candidate a vagas, enquanto o recrutador cadastra vagas, define os requisitos de cada uma e analisa os candidatos com apoio de um cálculo automático de compatibilidade.

**Público-alvo:** Candidatos em busca de vagas de emprego, e Recrutadores/profissionais de RH responsáveis pela publicação de vagas e pela triagem dos candidatos.

1. ### **1.1 Objetivo**

Este documento tem como objetivo apresentar de forma detalhada os requisitos funcionais e não funcionais da Plataforma de Triagem Inteligente de Currículos. Ele servirá como guia para o desenvolvimento do software, descrevendo as funcionalidades, características e necessidades que serão abordadas na implementação.

2. ### **1.2 Escopo do produto**

O escopo deste documento abrange o cadastro de candidatos e o envio de currículos, o cadastro de vagas pelo recrutador (incluindo as skills exigidas), a candidatura do cidadão a uma vaga aberta, o cálculo automático de compatibilidade entre candidato e vaga (por correspondência de palavras-chave), o painel de triagem do recrutador, a decisão sobre cada candidatura e o acompanhamento de status pelo candidato. O sistema visa auxiliar a tomada de decisão do recrutador, não substituindo a avaliação humana na seleção final dos candidatos.

3. ### **1.3 Definições, Acrônimos e Abreviações**

| Nº | Sigla | Definição |
| ----- | ----- | ----- |
| 01 | RF | Requisito funcional |
| 02 | RNF | Requisito não funcional |
| 03 | RH | Recursos Humanos |
| 04 | ATS | Applicant Tracking System (sistema de rastreamento de candidatos) |
| 05 | PDF | Portable Document Format (formato de documento portátil) |

   2. ## **2 Descrição geral**

      1. ### **2.1 Modo de Operação**

A plataforma opera como um sistema web voltado para a digitalização do processo de triagem de currículos em processos seletivos. Ela interliga candidatos e recrutadores, proporcionando um fluxo de cadastro, candidatura, cálculo automático de compatibilidade e decisão mais ágil e organizado. Os principais atores são o Candidato (cadastro e candidatura) e o Recrutador/RH (cadastro de vagas, triagem e decisão).

2. ### **2.2 Funções do produto**

| Nº | Função | Descrição |
| ----- | ----- | ----- |
| 01 | Cadastrar candidato | O candidato pode criar uma conta informando dados pessoais e de contato. |
| 02 | Enviar currículo | O candidato anexa um arquivo de currículo e/ou preenche manualmente suas skills e experiências. |
| 03 | Cadastrar vaga | O recrutador cadastra uma vaga informando título, descrição e as skills exigidas. |
| 04 | Candidatar-se à vaga | O candidato se candidata a uma vaga aberta disponível na plataforma. |
| 05 | Calcular compatibilidade | O sistema compara as skills exigidas pela vaga com as skills do candidato e calcula um percentual de compatibilidade. |
| 06 | Analisar candidatos | O recrutador visualiza um painel de triagem com os candidatos ordenados por compatibilidade. |
| 07 | Decidir sobre candidatura | O recrutador decide: aprovar, rejeitar ou manter o candidato em banco de talentos. |
| 08 | Acompanhar solicitação | O candidato acompanha, pela plataforma, o status de cada candidatura enviada. |

   3. ### **2.2.1 Diagrama de Casos de Uso**

O diagrama acima mostra os dois atores do sistema (Candidato e Recrutador/RH) e os casos de uso que cada um realiza. Os casos de uso do Candidato estão em verde-azulado e os do Recrutador/RH em coral.

4. ### **2.3 Usuários e sistemas externos**

| Nº | Usuário | Descrição |
| ----- | ----- | ----- |
| 01 | Candidato | Pessoa que se cadastra na plataforma, envia currículo e se candidata a vagas disponíveis. |
| 02 | Recrutador / RH | Responsável por cadastrar vagas, definir as skills exigidas, analisar os candidatos e decidir sobre cada candidatura. |

   5. ### **2.4 Restrições**

| Nº | Tipo | Descrição |
| ----- | ----- | ----- |
| 01 | Software | O ambiente operacional será uma aplicação web, compatível com os principais navegadores (Chrome, Firefox, Edge). |
| 02 | Processo | O cálculo de compatibilidade é baseado exclusivamente na correspondência de palavras-chave entre as skills exigidas pela vaga e as skills informadas pelo candidato — não avalia experiência prévia nem soft skills. |
| 03 | Processo | Uma vaga só pode receber novas candidaturas enquanto estiver com o status "Aberta". |
| 04 | Segurança | Currículos e dados pessoais dos candidatos devem ser armazenados de forma protegida, acessíveis apenas a usuários autorizados. |
| 05 | Segurança | O sistema deve restringir o acesso através de login individual para cada perfil de usuário. |

      6. ### **2.5 Requisitos Adiados**

| Nº | Referência | Detalhes |
| ----- | ----- | ----- |
| 01 | Análise por IA | Futuramente, o cálculo de compatibilidade poderá evoluir de correspondência por palavra-chave para uma análise semântica com apoio de IA. |
| 02 | Notificações | O sistema poderá enviar notificações por e-mail ao candidato sobre mudanças no status da candidatura. |
| 03 | Relatórios e estatísticas | A plataforma poderá gerar relatórios com a quantidade de candidaturas recebidas, aprovadas e rejeitadas por vaga. |

   3. ## **3 Requisitos específicos**

      1. ### **3.1 Requisitos Funcionais**

| Código | Nome | Descrição |
| ----- | ----- | ----- |
| RF01 | Cadastro de Candidato | O candidato deve poder criar uma conta informando dados pessoais básicos (nome, contato, formação). |
| RF02 | Envio de Currículo | O candidato deve poder anexar seu currículo (arquivo) e/ou preencher manualmente suas skills e experiências. |
| RF03 | Cadastro de Vaga | O recrutador deve ser capaz de cadastrar uma vaga informando título, descrição e as skills exigidas. |
| RF04 | Edição/Encerramento de Vaga | O recrutador deve poder editar os dados da vaga ou encerrá-la, impedindo o recebimento de novas candidaturas. |
| RF05 | Candidatura à Vaga | O candidato deve poder se candidatar a uma vaga aberta disponível na plataforma. |
| RF06 | Cálculo de Compatibilidade | O sistema deve comparar as skills exigidas pela vaga com as skills do candidato e calcular um percentual de compatibilidade. |
| RF07 | Banco de Talentos | O sistema deve armazenar os currículos cadastrados, permitindo consulta futura pelo recrutador mesmo após o encerramento de uma vaga. |
| RF08 | Painel de Triagem | O recrutador deve ter acesso a um painel listando os candidatos de uma vaga, ordenados pelo percentual de compatibilidade. |
| RF09 | Decisão do Recrutador | O recrutador deve poder decidir sobre cada candidatura: aprovar, rejeitar ou manter em banco de talentos. |
| RF10 | Acompanhamento de Status | O candidato deve poder acompanhar o status de suas candidaturas (em análise, aprovado, rejeitado). |

      2. ### **3.2 Requisitos Não-Funcionais**

         1. #### ***3.2.1 Desempenho***

| Código | Requisito / Aplicação | Descrição |
| ----- | ----- | ----- |
| RNF01 | Aplicação | O cálculo de compatibilidade entre candidato e vaga deve ocorrer em tempo reduzido. |
| RNF02 | Aplicação | O upload e a exibição de currículos devem apresentar tempos de resposta rápidos. |

         2. #### ***3.2.2 Usabilidade***

| Código | Requisito / Aplicação | Descrição |
| ----- | ----- | ----- |
| RNF03 | Design intuitivo | O sistema deve possuir uma interface intuitiva e de fácil uso, tanto para o candidato quanto para o recrutador. |
| RNF04 | Orientação ao usuário | O status da candidatura deve ser apresentado de forma clara e objetiva ao candidato. |

         3. #### ***3.2.3 Segurança***

| Código | Requisito / Aplicação | Descrição |
| ----- | ----- | ----- |
| RNF05 | Autenticação segura | A autenticação segura deve ser implementada para acesso aos perfis de Candidato e Recrutador. |

         4. #### ***3.2.4 Expansibilidade***

| Código | Requisito / Aplicação | Descrição |
| ----- | ----- | ----- |
| RNF06 | Arquitetura Modular | O sistema deve ser desenvolvido de forma modular, permitindo futuramente a inclusão de novos critérios de compatibilidade (ex: análise por IA) sem grandes retrabalhos. |

      3. ### **3.3 Requisitos de Interface**

         1. #### ***3.3.1 Interfaces de Usuário***

| Nº | Nome | Descrição |
| ----- | ----- | ----- |
| 01 | Tela de cadastro do candidato | Interface online para o candidato criar sua conta e enviar o currículo. |
| 02 | Tela de cadastro de vaga | Interface online para o recrutador cadastrar a vaga e as skills exigidas. |
| 03 | Tela de candidatura | Interface online para o candidato visualizar vagas abertas e se candidatar. |
| 04 | Painel de triagem | Interface online para o recrutador visualizar os candidatos ordenados por compatibilidade e decidir sobre cada um. |
| 05 | Tela de acompanhamento | Interface online para o candidato acompanhar o status de suas candidaturas. |

         2. #### ***3.3.2 Interfaces de Hardware***

O sistema deve permitir a seleção de arquivos do dispositivo do usuário (upload do currículo em formato PDF) através do navegador. Não há outras exigências específicas de hardware no escopo atual.

3. #### ***3.3.3 Interfaces de Software***

Não aplicável no escopo atual.

4. #### ***3.3.4 Interfaces de Comunicação***

Não aplicável no escopo atual. O envio de notificações por e-mail sobre o status da candidatura está listado como Requisito Adiado (item 2.5).

4. ## **4 Restrições e Limitações**

* Caso o cálculo de compatibilidade não considere sinônimos entre skills (ex.: "JS" e "JavaScript"), candidatos qualificados podem ficar sub-representados no ranking.

* A qualidade da triagem depende diretamente das informações preenchidas corretamente pelo candidato.

* \[Listar quaisquer outras restrições ou limitações que possam impactar o desenvolvimento ou a implantação do software\]

## **5 Glossário**

| Termo | Definição |
| ----- | ----- |
| Compatibilidade | Percentual calculado a partir da correspondência entre as skills exigidas pela vaga e as skills informadas pelo candidato. |
| Banco de Talentos | Repositório de currículos armazenados para consulta futura pelo recrutador, mesmo após o encerramento de uma vaga. |
| Skill | Habilidade técnica ou competência informada pelo candidato ou exigida pela vaga. |
| ATS | Applicant Tracking System — sistema de rastreamento e triagem de candidatos. |

5. ## **6 Referências**

Não encontradas.

6. ## **7 Aprovação**

Este documento foi revisado e aprovado por:

Nome: \_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_ Cargo: \_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

