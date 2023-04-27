package desafiospringdio.designpatterns.services;

import desafiospringdio.designpatterns.models.Cliente;

public interface ClienteService {
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);
    Cliente inserir(Cliente cliente);
    Cliente atualizar(Long id, Cliente cliente);
    void deletar(Long id);
}
