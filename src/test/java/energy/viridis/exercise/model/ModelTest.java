package energy.viridis.exercise.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
public class ModelTest {

    static class ObjetoTestavel {
        Class classe;
        String[] metodosAIgnorar;

        private ObjetoTestavel(Class classe, String... metodosAIgnorar) {
            this.classe = classe;
            this.metodosAIgnorar = metodosAIgnorar;
        }
    }

    private final Class[] classesTodosOsMetodos;

    private final ObjetoTestavel[] classesIgnorarMetodos;

    private static final long CODIGO = 1L;




    /**
     * Cria uma nova instancia.
     */
    public ModelTest() {
        classesIgnorarMetodos = new ObjetoTestavel[]{

        };

        classesTodosOsMetodos = new Class[]{
            Equipment.class,
            MaintenanceOrder.class
        };
    }

    /**
     * Classe para realização dos testes de entidades
     *
     * @throws IllegalAccessException    no caso de algum {@link IllegalAccessException}
     * @throws InstantiationException    no caso de algum {@link InstantiationException}
     * @throws InvocationTargetException exceção lançada
     * @throws IntrospectionException    exceção lançada
     * @throws NoSuchMethodException     exceção lançada
     * @throws NoSuchFieldException      exceção lançada
     */
    @Test
    public void testarEntidades() throws IllegalAccessException, InstantiationException, InvocationTargetException,
            IntrospectionException, NoSuchMethodException, NoSuchFieldException {
        for (Class classe : classesTodosOsMetodos) {
            if (!classe.isEnum()) {
                Constructor constructor = classe.getDeclaredConstructors()[0];
                constructor.setAccessible(Boolean.TRUE);
                Object instancia;
                if (constructor.getParameters().length > 0) {
                    Object[] lObject = new Object[constructor.getParameters().length];
                    instancia = constructor.newInstance(lObject);
                } else {
                    instancia = constructor.newInstance();
                }
                testarTodosConstrutores(classe);
                testarTodosFields(classe);
                testarTodosMetodos(classe, instancia);
            } else {
                testarEnum(classe);
            }
        }

        for (ObjetoTestavel objetoTestavel : classesIgnorarMetodos) {
            if (!objetoTestavel.classe.isEnum()) {
                Constructor constructor = objetoTestavel.classe.getDeclaredConstructors()[0];
                constructor.setAccessible(Boolean.TRUE);
                Object instancia;
                if (constructor.getParameters().length > 0) {
                    Object[] lObject = new Object[constructor.getParameters().length];
                    instancia = constructor.newInstance(lObject);
                } else {
                    instancia = constructor.newInstance();
                }
                testarTodosConstrutores(objetoTestavel.classe);
                testarTodosFields(objetoTestavel.classe);
                testarTodosMetodos(objetoTestavel.classe, instancia, objetoTestavel.metodosAIgnorar);
            } else {
                testarEnum(objetoTestavel.classe);
            }
        }
    }

    /*
     * Métodos Auxiliares
     */
    private void testarTodosFields(Class classe) throws IllegalAccessException {
        for (Field field : classe.getDeclaredFields()) {
            if (Modifier.isPublic(classe.getModifiers()) && Modifier.isStatic(classe.getModifiers())) {
                field.get(null);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void testarTodosConstrutores(Class classe)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Constructor constructor : classe.getDeclaredConstructors()) {
            constructor.setAccessible(Boolean.TRUE);
            List parametros = new ArrayList<>();
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                parametros.add(null);
            }
            constructor.newInstance(parametros.toArray());
        }
    }

    @SuppressWarnings("unchecked")
    private void testarTodosMetodos(Class classe, Object instancia, String... metodosAInorar)
            throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        for (Method method : obterTodosMetodosTestaveis(classe)) {
            List parametros = new ArrayList<>();
            for (Class type : method.getParameterTypes()) {
                if (type.equals(classe)) {
                    setIdField(classe, instancia);
                    parametros.add(instancia);
                } else {
                    parametros.add(null);
                }
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                if (!Arrays.asList(metodosAInorar).contains(method.getName())) {
                    method.setAccessible(true);
                    method.invoke(instancia, parametros.toArray());
                    if ("compareTo".equals(method.getName())) {
                        method.invoke(instancia, new Object[]{null});
                    }
                    if ("equals".equals(method.getName())) {
                        method.invoke(instancia, "");
                    }
                }
            } else {
                if ("valoresOrdenados".equals(method.getName())) {
                    method.invoke(instancia);
                }
            }
        }
    }

    private void setIdField(Class classe, Object instancia) throws IllegalAccessException {
        for (Field field : classe.getDeclaredFields()) {
            if (field.getDeclaredAnnotation(Id.class) != null) {
                field.setAccessible(Boolean.TRUE);
                field.set(instancia, CODIGO);
            }
        }
    }

    private void testarEnum(Class classe)
            throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        for (Object instancia : classe.getEnumConstants()) {
            testarTodosMetodos(classe, instancia);
        }
    }

    private List<Method> obterTodosMetodosTestaveis(Class pClasse) {
        List<Method> lMetodos = new ArrayList<>();
        lMetodos.addAll(Arrays.asList(pClasse.getDeclaredMethods()));
        if (!Arrays.asList(Object.class, Exception.class, RuntimeException.class).contains(pClasse
                .getSuperclass())
                && !pClasse.isEnum()) {
            lMetodos.addAll(obterTodosMetodosTestaveis(pClasse.getSuperclass()));
        }
        return lMetodos.stream().filter(method -> !method.isSynthetic()).collect(Collectors.toList());
    }
}
