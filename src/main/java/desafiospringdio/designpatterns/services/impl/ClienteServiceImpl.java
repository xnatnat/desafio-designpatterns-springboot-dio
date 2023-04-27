package desafiospringdio.designpatterns.services.impl;

import desafiospringdio.designpatterns.models.Cliente;
import desafiospringdio.designpatterns.repositories.ClienteRepository;
import desafiospringdio.designpatterns.repositories.EnderecoRepository;
import desafiospringdio.designpatterns.services.ClienteService;
import desafiospringdio.designpatterns.services.ViaCepService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    public ClienteServiceImpl(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.viaCepService = viaCepService;
    }

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

    @Override
    public Cliente inserir(Cliente cliente) {
        return salvarCliente(cliente);
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        var clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent())
            return salvarCliente(cliente);
        return null;
    }

    private Cliente salvarCliente(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        var endereco = enderecoRepository.findById(cep).orElseGet(()-> {
            var novoEndereco = viaCepService.consultarCep(cep);
            return enderecoRepository.save(novoEndereco);
        });
        cliente.setEndereco(endereco);
        return clienteRepository.save(cliente);
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
