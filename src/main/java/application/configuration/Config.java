package application.configuration;

import application.repository.ClienteRepository;
import application.repository.MovimientoRepository;
import application.service.ClienteAdminServiceImpl;
import application.service.ClienteServiceImpl;
import application.service.MovimientoServiceImpl;
import application.service.outputs.ClienteAdminService;
import application.service.outputs.ClienteService;
import application.service.outputs.MovimientoService;
import application.service.ports.ClienteRepositoryPort;
import application.service.ports.MovimientoRepositoryPort;
import application.userInterface.MenuApp;
import application.view.ClienteView;
import application.view.MovimientoView;

public class Config {

    public static MenuApp createMenuApp() {

        ClienteRepositoryPort    clienteRepository    = new ClienteRepository();
        MovimientoRepositoryPort movimientoRepository = new MovimientoRepository();

        ClienteService      clienteService      = new ClienteServiceImpl(clienteRepository);
        ClienteAdminService clienteAdminService = new ClienteAdminServiceImpl(clienteRepository);
        MovimientoService   movimientoService   = new MovimientoServiceImpl(movimientoRepository);

        ClienteView    clienteView    = new ClienteView(clienteService, clienteAdminService);
        MovimientoView movimientoView = new MovimientoView(movimientoService);

        return new MenuApp(clienteView, movimientoView);
    }
}
