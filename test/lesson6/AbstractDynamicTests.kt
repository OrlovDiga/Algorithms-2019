package lesson6

import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        val expectedLength = "e kerwelkkd r".length
        assertEquals(
            expectedLength, longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
            ).length, "Answer must have length of $expectedLength, e.g. 'e kerwelkkd r' or 'erhlkrw kjk r'"
        )
        val expectedLength2 = """ дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent().length
        assertEquals(
            expectedLength2, longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
            ).length, "Answer must have length of $expectedLength2"
        )
        assertEquals("kelolkek", longestCommonSubSequence("lolololololololololokelolkek", "kekeekekekekekekkeeklolkkek"))
        assertEquals("а е мо сурв,\n" +
                "у ряа о\n" +
                "на в глши есо соновх\n" +
                "вно дно т жде ен под кно сой сетиц\n" +
                "ореь уто н ча,\n" +
                " ет омтно\n" +
                "В ви наморенны а.\n" +
                "лядиь  забт оот\n" +
                "Н нй отанн уть\n" +
                "са престия заботы\n" +
                "Теснт вою ве гру.\n" +
                "одитс тее", longestCommonSubSequence("Ты жива еще, моя старушка?\n" +
                "Жив и я. Привет тебе, привет!\n" +
                "Пусть струится над твоей избушкой\n" +
                "Тот вечерний несказанный свет.\n" +
                "\n" +
                "Пишут мне, что ты, тая тревогу,\n" +
                "Загрустила шибко обо мне,\n" +
                "Что ты часто ходишь на дорогу\n" +
                "В старомодном ветхом шушуне.\n" +
                "\n" +
                "И тебе в вечернем синем мраке\n" +
                "Часто видится одно и то ж:\n" +
                "Будто кто-то мне в кабацкой драке\n" +
                "Саданул под сердце финский нож.\n" +
                "\n" +
                "Ничего, родная! Успокойся.\n" +
                "Это только тягостная бредь.\n" +
                "Не такой уж горький я пропойца,\n" +
                "Чтоб, тебя не видя, умереть.\n" +
                "\n" +
                "я по-прежнему такой же нежный\n" +
                "И мечтаю только лишь о том,\n" +
                "Чтоб скорее от тоски мятежной\n" +
                "Воротиться в низенький наш дом.\n" +
                "\n" +
                "я вернусь, когда раскинет ветви\n" +
                "По-весеннему наш белый сад.\n" +
                "Только ты меня уж на рассвете\n" +
                "Не буди, как восемь лет назад.\n" +
                "\n" +
                "Не буди того, что отмечталось,\n" +
                "Не волнуй того, что не сбылось,-\n" +
                "Слишком раннюю утрату и усталость\n" +
                "Испытать мне в жизни привелось.\n" +
                "\n" +
                "И молиться не учи меня. Не надо!\n" +
                "К старому возврата больше нет.\n" +
                "Ты одна мне помощь и отрада,\n" +
                "Ты одна мне несказанный свет.\n" +
                "\n" +
                "Так забудь же про свою тревогу,\n" +
                "Не грусти так шибко обо мне.\n" +
                "Не ходи так часто на дорогу\n" +
                "В старомодном ветхом шушуне.", "Подруга дней моих суровых,\n" +
                "Голубка дряхлая моя!\n" +
                "Одна в глуши лесов сосновых\n" +
                "Давно, давно ты ждешь меня.\n" +
                "Ты под окном своей светлицы\n" +
                "Горюешь, будто на часах,\n" +
                "И медлят поминутно спицы\n" +
                "В твоих наморщенных руках.\n" +
                "Глядишь в забытые вороты\n" +
                "На черный отдаленный путь:\n" +
                "Тоска, предчувствия, заботы\n" +
                "Теснят твою всечасно грудь.\n" +
                "То чудится тебе…"))

    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(
            listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(
                listOf(
                    23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                    45, 76, 15, 99, 100, 88, 84, 35, 88
                )
            )
        )
        assertEquals(listOf(1, 2, 3, 4, 5, 6), longestIncreasingSubSequence(listOf(10, 1, 5, 2, 6, 4, 3, 4, 5, 6)))

    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(1, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(28, shortestPathOnField("input/field_in4.txt"))
        assertEquals(222, shortestPathOnField("input/field_in5.txt"))
        assertEquals(15, shortestPathOnField("input/field_in6.txt"))
    }

}