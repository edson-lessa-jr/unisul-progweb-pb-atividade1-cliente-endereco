package br.unisul.aula.servlet;

import br.unisul.aula.banco.ClienteDAO;
import br.unisul.aula.dtos.CidadeDTO;
import br.unisul.aula.dtos.InfoClienteDTO;
import br.unisul.aula.modelo.Cliente;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//http://localhost:8080/<projeto>/cidade?cidade=<nome da cidade>
@WebServlet(name = "CidadeClienteServlet", value = "/cidade")
public class CidadeClienteServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        String nomeCidade = request.getParameter("cidade");
        List<Cliente> cidadeList = clienteDAO.findByCidade(nomeCidade);

        CidadeDTO cidadeDTO = null;
        for (Cliente cliente: cidadeList) {
            if (cidadeDTO==null){
                cidadeDTO = new CidadeDTO(cliente);
            }
            InfoClienteDTO infoClienteDTO = new InfoClienteDTO(cliente);
            cidadeDTO.setClientes(infoClienteDTO);
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        String cidadeJson = gson.toJson(cidadeDTO);
        response.getWriter().println(cidadeJson);
    }

}
