package anotacoes;

import java.lang.annotation.*;

import enums.DefaultEnum;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CampoFormulario {
    
    String descricao();

    boolean obrigatorio() default false;

    TipoCampo tipo() default TipoCampo.TEXTO;

    String[] opcoes() default {};

    int largura() default 300;

    int altura() default 35;
    int linha() default -1;
    String dependeDe() default "";
    boolean pesquisavel() default false;
    Class<? extends Enum<?>> enumType() default DefaultEnum.class;
    Class<? extends ComboDadosProvedor> provider()
            default ComboDadosProvedor.class;

}