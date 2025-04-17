package com.umc.pfc_fastprice.controller;

import com.umc.pfc_fastprice.model.Usuario;
import com.umc.pfc_fastprice.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaginasController {

    @Autowired
    UsuarioService usuarioService;

    // Método para redirecionar à página para login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Método para redirecionar à página para cadastro
    @GetMapping("/cadastrar")
    public String cadastrar() {
        
        return "cadastrar";
    }

    // Método para configurar a página inicial
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("layout"); // Cria um novo Layout
        modelAndView.addObject("content", "home :: content"); // Define o conteúdo da página com base no "content" da página "home"
        return modelAndView;
    }

    // Método para configurar a página de exibição de usuários
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarUsuarios(); // Retorna os usuários cadastrados no banco de dados
        model.addAttribute("usuarios", usuarios); // Salva a lista retornada no atributo "usuarios" da model
        String fragment = "usuarios :: content";
        model.addAttribute("content", fragment); // Define o conteúdo da página com base no "content" da página "usuarios"
        return "usuarios";
    }

    // Método para configurar a página de edição de usuário
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable String id, Model model) {
        Usuario usuario = usuarioService.buscarUsuario(id); // Retorna o usuário de id específico
        model.addAttribute("usuario", usuario); // Salva o usuario encontrado no atributo "usuario" da model
        String fragment = "usuarios-editar :: editar(${usuario})";
        model.addAttribute("content", fragment); // Define o conteúdo da página com base no "content" da página "usuarios-editar"
        return "usuarios-editar";
    }

    // Método para atualizar o registro do usuário editado
    @PostMapping("/usuarios/atualizar/{id}")
    public String atualizarUsuario(@PathVariable String id, @ModelAttribute Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.buscarUsuario(id); // Retorna o usuário de id específico
	
	if (usuario != null) {
	    usuario.setNome(usuarioAtualizado.getNome()); // Atualiza o nome
	    usuario.setEmail(usuarioAtualizado.getEmail()); // Atualiza o email
	    usuarioService.atualizarUsuario(usuario); // Atualiza o registro do usuário após a edição
	}

        return "redirect:/usuarios";
    }

    // Método para deletar o registro de um usuário
    @GetMapping("/usuarios/deletar/{id}")
    public String deletarUsuario(@PathVariable String id) {
	usuarioService.deletarUsuario(id); // Delete o registro do usuário de id específico
	return "redirect:/usuarios";
    }
}
