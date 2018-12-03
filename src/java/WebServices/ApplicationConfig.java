/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author hook
 */
@javax.ws.rs.ApplicationPath("banco")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(WebServices.ConfiguracaoResource.class);
        resources.add(WebServices.FornecedorResource.class);
        resources.add(WebServices.MovimentacaoResource.class);
        resources.add(WebServices.PedidoResource.class);
        resources.add(WebServices.PermissoesResource.class);
        resources.add(WebServices.ProdutoResource.class);
        resources.add(WebServices.UsuarioResource.class);

    }

}
