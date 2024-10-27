theme: /

    state: newNode_12
        a:  Привет! Я бот консультант. Чем я могу Вам помочь?
            Если вы хотите связаться со службой тех.поддержки /support
        go!: /newNode_3
    @BlockEvents
        {
          "global" : true,
          "boundsTo" : "/newNode_12",
          "title" : "События",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ]
        }
    state: newNode_3
        state: 1
            event!: noMatch
            go!: /newNode_9
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_3",
                name: "newNode_3 buttons",
                handler: function($context) {
                }
            });
    @IntentGroup
        {
          "global" : true,
          "boundsTo" : "",
          "title" : "Интенты",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "intentSubjects" : [ "" ]
        }
    state: newNode_18
        state: 1
            intent!: /business_intents/common/required_documents
            e!: /add_docx

            go!: /newNode_16
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_18",
                name: "newNode_18 buttons",
                handler: function($context) {
                }
            });

    state: newNode_9
        LlmRequest:
            systemContent = Перед тобой некоторая документация.Отвечай на вопросы, которые человек может задать к данному документу.Используй информацию ТОЛЬКО из документа. Используй непредвзятый и журналистский тон. Не повторяй текст. Ты - чат-бот, отвечающий на вопросы используя ТОЛЬКО предоставленные данные. Цензор: фразы которых нет в базе знаний, вопросы на которые ты не можешь ответить исходя из документа. Отвечай на вопросы пользователей как помощник, ответы ты должен давать ТОЛЬКО по файлам из базы знаний которая содержит руководство пользователя по Системе управления безопасностью конфигураций ПО, не выдумывай фразы и ответы которых нет в документе. Не повторять сообщения. Если ответа на вопрос нет в документах из базы знаний, то выводи сообщение: [К сожалению я не могу ответить на ваш вопрос, так как для этого недостаточно данных, попробуйте перефразировать вопрос, или обратитесь к оператору]
                Отвечая на вопросы, не относящиеся к Системе управления безопасностью конфигураций ПО, к примеру [Пушкин, Лермонтов, искусство] отвечай: [К сожалению я не могу ответить на ваш вопрос, так как для этого недостаточно данных, попробуйте перефразировать вопрос, или обратитесь к оператору]
            userContent = {{$session.queryText}}
            timeout = 6000
            sendHistory = true
            sendMessage = true
            profanityCheck = true
            varName = answerGigaChat
            okState = 
            errorState = /newNode_6
            censorFailState = /newNode_11

    state: newNode_16
        FileUpload:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Прикрепите файл
            varName = userFile
            allowedType = application
            then = 

    state: newNode_11
        a: К сожалению я не могу ответить на ваш вопрос, так как для этого недостаточно данных, попробуйте перефразировать вопрос, или обратитесь к оператору
        go!: /newNode_13
    @IntentGroup
        {
          "global" : true,
          "boundsTo" : "/newNode_11",
          "title" : "Интенты",
          "actions" : [ ],
          "intentSubjects" : [ "" ]
        }
    state: newNode_13
        state: 1
            q!: $SWITCH
            e!: /support

            go!: /newNode_14

    state: newNode_6
        if: $session.gigaChatResponseStatus == '500'
            go!: /newNode_4
        elseif: $session.gigaChatResponseStatus == '413' || $session.gigaChatResponseStatus == '408'
            go!: /newNode_7
        else:
            go!: /newNode_10

    state: newNode_4
        a: Возникла ошибка с последним сообщением. Пожалуйста, повторите свой вопрос.

    state: newNode_7
        a: Ваш вопрос слишком объемный, поэтому мне трудно на него ответить. Попробуйте, пожалуйста, сформулировать более кратко.

    state: newNode_10
        a: Для решения вашего вопроса, мне необходима помощь. Перевожу на другого специалиста.
        go!: /newNode_8

    state: newNode_14
        TransferToOperator:
            sendHistory = false
            openChatButton = Чат с оператором
            errorState = /newNode_15
            messageToOperator = Здравствуйте! Мне нужна консультация
            messageToUser = Перевожу диалог на оператора
            chatClosedOperatorState = 
            noOperatorOnline = false
            noOperatorState = 

    state: newNode_15
        a:  Техническая поддержка
            +7 (495) 258-06-36
            info@lense.ru
            lense.ru
            Если Вам требуется квалифицированная помощь, позвоните на телефон «горячей линии поддержки», напишите письмо или воспользуйтесь формой регистрации заявки на сайте.