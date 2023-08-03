package br.com.alura.orgs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.ui.activity.ListaProdutosActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProdutoTelasTestes {

    @get:Rule
    val rule = ActivityScenarioRule(ListaProdutosActivity::class.java)

    @Before
    fun preparaAmbiente() {
        AppDatabase.instancia(InstrumentationRegistry.getInstrumentation().targetContext)
            .clearAllTables()
    }

    @Test
    fun deveMostrarONomeDoAplicativo() {
        onView(withText("Orgs")).check(matches(isDisplayed()))
    }

    @Test
    fun deveTerTodosOsCamposNecessariosParaCriarUmProduto() {
        onView(withId(R.id.activity_lista_produtos_fab)).perform(click())
        onView(withId(R.id.activity_formulario_produto_nome)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_formulario_produto_descricao)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_formulario_produto_valor)).check(matches(isDisplayed()))
        onView(withId(R.id.activity_formulario_produto_botao_salvar)).check(matches(isDisplayed()))
    }

    @Test
    fun deveSerCapazDePreencherOsCamposESalvar() {
        criaProduto(nome = "Banana", descricao = "Nanica", valor = "3.99")
    }

    @Test
    fun deveSerCapazDeEditarUmProduto() {
        criaProduto(nome = "Maca", descricao = "Verde", valor = "5.89")
        editaProduto(nome = "Maca", descricao = "Fuji", valor = "2.69")
    }

    private fun criaProduto(
        nome: String? = null,
        descricao: String? = null,
        valor: String? = null
    ) {
        onView(withId(R.id.activity_lista_produtos_fab)).perform(click())
        preencheFormularioProduto(nome = nome, descricao = descricao, valor = valor)
    }

    private fun editaProduto(
        nome: String,
        nomeNovo: String? = null,
        descricao: String? = null,
        valor: String? = null
    ) {
        onView(withText(nome)).perform(click())
        onView(withId(R.id.menu_detalhes_produto_editar)).perform(click())
        preencheFormularioProduto(nome = nomeNovo, descricao = descricao, valor = valor)
    }

    private fun preencheFormularioProduto(
        nome: String? = null,
        descricao: String? = null,
        valor: String? = null
    ) {
        nome?.let {
            onView(withId(R.id.activity_formulario_produto_nome))
                .perform(
                    replaceText(it),
                    closeSoftKeyboard()
                )
        }
        descricao?.let {
            onView(withId(R.id.activity_formulario_produto_descricao))
                .perform(
                    replaceText(it),
                    closeSoftKeyboard()
                )
        }
        valor?.let {
            onView(withId(R.id.activity_formulario_produto_valor))
                .perform(
                    replaceText(it),
                    closeSoftKeyboard()
                )
        }

        onView(withId(R.id.activity_formulario_produto_botao_salvar))
            .perform(click())

        onView(withText(descricao)).check(matches(isDisplayed()))
    }

}