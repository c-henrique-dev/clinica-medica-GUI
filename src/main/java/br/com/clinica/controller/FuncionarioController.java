package br.com.clinica.controller;

import br.com.clinica.model.dao.FuncionarioDAO;
import br.com.clinica.model.tabela.FuncionarioModeloTabela;
import br.com.clinica.model.util.validacoes.FuncionarioValidar;
import br.com.clinica.model.objetos.Cidade;
import br.com.clinica.model.objetos.Endereco;
import br.com.clinica.model.objetos.Funcionario;
import br.com.clinica.model.objetos.Login;
import br.com.clinica.view.telas.TelaFuncionario;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class FuncionarioController {
    
    private TelaFuncionario view;
    
    private FuncionarioModeloTabela modelo;
    
    public FuncionarioController(TelaFuncionario view) {
        this.view = view;
        
    }
    
    public void salvar() {
        
        try {
            Funcionario funcionario = new Funcionario();
            FuncionarioValidar validar = new FuncionarioValidar();
            
            Endereco endereco = new Endereco();
            Login login = new Login();
            
            funcionario.setNome(this.view.getTfNome().getText());
            funcionario.setCpf(this.view.getTfCpf().getText());
            String dataCampo = this.view.getTfDataNascimento().getText();
            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
            Date novaData = data.parse(dataCampo);
            
            funcionario.setDataNascimento(novaData);
            if (this.view.getrMasculino().isSelected()) {
                funcionario.setSexo(this.view.getrMasculino().getText());
            } else {
                funcionario.setSexo(this.view.getrFeminino().getText());
            }
            endereco.setBairro(this.view.getTfBairro().getText());
            endereco.setCep(this.view.getTfCep().getText());
            endereco.setUf(this.view.getCbEstado().getSelectedItem().toString());
            endereco.setCidade(this.view.getCbCidade().getSelectedItem().toString());
            endereco.setNumero(Integer.parseInt(this.view.getTfNumero().getText()));
            endereco.setRua(this.view.getTfRua().getText());
            login.setEmail(this.view.getTfEmail().getText());
            login.setSenha(String.valueOf(this.view.getTfSenha().getPassword()));
            funcionario.setTelefone(this.view.getTfTelefone().getText());
            funcionario.setLogin(login);
            funcionario.setEndereco(endereco);
            
            validar.validareSalvarFuncionario(funcionario, "salvar");
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void editar() {
        try {
            
            Funcionario funcionario = new Funcionario();
            
            FuncionarioValidar validar = new FuncionarioValidar();
            
            Endereco endereco = new Endereco();
            Login login = new Login();
            
            int linha = this.view.getTabelaFuncionario().getSelectedRow();
            
            int codigo = Integer.parseInt(String.valueOf(this.view.getTabelaFuncionario().
                    getValueAt(linha, 0)));
            if (this.view.getTabelaFuncionario().getSelectedRow() != -1) {
                
                funcionario.setId(codigo);
                funcionario.setNome(this.view.getTfNome().getText());
                funcionario.setCpf(this.view.getTfCpf().getText());
                String dataCampo = this.view.getTfDataNascimento().getText();
                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                Date novaData = data.parse(dataCampo);
                
                funcionario.setDataNascimento(novaData);
                if (this.view.getrMasculino().isSelected()) {
                    funcionario.setSexo(this.view.getrMasculino().getText());
                } else {
                    funcionario.setSexo(this.view.getrFeminino().getText());
                }
                endereco.setBairro(this.view.getTfBairro().getText());
                endereco.setCep(this.view.getTfCep().getText());
                endereco.setUf(this.view.getCbEstado().getSelectedItem().toString());
                endereco.setCidade(this.view.getCbCidade().getSelectedItem().toString());
                endereco.setNumero(Integer.parseInt(this.view.getTfNumero().getText()));
                endereco.setRua(this.view.getTfRua().getText());
                login.setEmail(this.view.getTfEmail().getText());
                login.setSenha(String.valueOf(this.view.getTfSenha().getPassword()));
                funcionario.setTelefone(this.view.getTfTelefone().getText());
                funcionario.setEndereco(endereco);
                funcionario.setLogin(login);
                
                validar.validareSalvarFuncionario(funcionario, "alterar");
                bloquearCampos(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Escolha um funcionário");
            }
            
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    public void bloquearCampos(boolean bloquear) {
        this.view.getTfNome().setEnabled(!bloquear);
        this.view.getTfBairro().setEnabled(!bloquear);
        this.view.getrMasculino().setEnabled(!bloquear);
        this.view.getrFeminino().setEnabled(!bloquear);
        this.view.getTfCep().setEnabled(!bloquear);
        this.view.getTfCpf().setEnabled(!bloquear);
        this.view.getTfDataNascimento().setEnabled(!bloquear);
        this.view.getTfNumero().setEnabled(!bloquear);
        this.view.getTfRua().setEnabled(!bloquear);
        this.view.getCbCidade().setEnabled(!bloquear);
        this.view.getCbEstado().setEnabled(!bloquear);
        this.view.getTfEmail().setEnabled(!bloquear);
        this.view.getTfSenha().setEnabled(!bloquear);
        this.view.getTfTelefone().setEnabled(!bloquear);
        
    }
    
    public void limparCampos() {
        this.view.getTfNome().setText("");
        this.view.getTfBairro().setText("");
        this.view.getTfCep().setText("");
        this.view.getTfCpf().setText("");
        this.view.getTfDataNascimento().setText("");
        this.view.getTfNumero().setText("");
        this.view.getTfRua().setText("");
        this.view.getTfTelefone().setText("");
        this.view.getTfEmail().setText("");
        this.view.getTfSenha().setText("");
        this.view.getrFeminino().setSelected(false);
        this.view.getrMasculino().setSelected(false);
        this.view.getCbCidade().removeAllItems();
        
    }
    
    public void listarCidadesPorEstado() {
        FuncionarioDAO repositorio = new FuncionarioDAO();
        ArrayList<Cidade> cidades = repositorio.listarCidadesPorUF(this.view.getCbEstado().getSelectedItem().toString());
        
        String uf = this.view.getCbEstado().getSelectedItem().toString();
        
        this.view.getCbCidade().removeAllItems();
        
        for (Cidade cidade : cidades) {
            
            this.view.getCbCidade().addItem(cidade.getCidade());
        }
        
    }
    
    public void btnAlterar() {
        
        try {
            FuncionarioDAO repositorio = new FuncionarioDAO();
            this.view.setFuncao("alterar");
            int linha = this.view.getTabelaFuncionario().getSelectedRow();
            long codigo = Long.parseLong(String.valueOf(this.view.getTabelaFuncionario().getValueAt(linha, 0)));
            Funcionario funcionarioEncontrado = repositorio.listarPorId(codigo);
            
            if (funcionarioEncontrado == null) {
                bloquearCampos(true);
            } else {
                bloquearCampos(false);
            }
            
            this.view.getTfNome().setText(funcionarioEncontrado.getNome());
            this.view.getTfCpf().setText(funcionarioEncontrado.getCpf());
            this.view.getTfCpf().setEnabled(false);
            this.view.getTfDataNascimento().setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(funcionarioEncontrado.getDataNascimento().getTime())));
            this.view.getTfCep().setText(funcionarioEncontrado.getEndereco().getCep());
            this.view.getTfRua().setText(funcionarioEncontrado.getEndereco().getRua());
            this.view.getTfBairro().setText(funcionarioEncontrado.getEndereco().getBairro());
            this.view.getTfNumero().setText(String.valueOf(funcionarioEncontrado.getEndereco().getNumero()));
            this.view.getCbEstado().setSelectedItem(funcionarioEncontrado.getEndereco().getUf());
            this.view.getCbCidade().setSelectedItem(funcionarioEncontrado.getEndereco().getCidade());
            this.view.getTfEmail().setText(funcionarioEncontrado.getLogin().getEmail());
            this.view.getTfSenha().setText(funcionarioEncontrado.getLogin().getSenha());
            this.view.getTfTelefone().setText(funcionarioEncontrado.getTelefone());
            
            if (funcionarioEncontrado.getSexo().equals("Masculino")) {
                this.view.getrMasculino().setSelected(true);
            }
            if (funcionarioEncontrado.getSexo().equals("Feminino")) {
                this.view.getrFeminino().setSelected(true);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Escolha um funcionário para editar!");
            
        }
        
    }
    
    public void btnExcluir() {
        FuncionarioDAO repositorio = new FuncionarioDAO();
        if (this.view.getTabelaFuncionario().getSelectedRow() != - 1) {
            int linha = this.view.getTabelaFuncionario().getSelectedRow();
            long codigo = Long.parseLong(String.valueOf(this.view.getTabelaFuncionario().getValueAt(linha, 0)));
            
            Funcionario funcionario = new Funcionario();
            funcionario.setId(codigo);
            
            switch (JOptionPane.showConfirmDialog(null, " Tem certeza que deseja excluir ? ", "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                
                case 0:
                    
                    repositorio.excluir(funcionario);
                    atualizarTabela();
                    break;
                
                case 1:
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escolha um funcionário");
        }
        
    }
    
    public void btnPesquisar() {
        
        FuncionarioDAO repositorio = new FuncionarioDAO();
        
        String cpf = this.view.getTfPesquisarCpf().getText();
        
        Funcionario funcionario = repositorio.listarPorCPF(cpf);
        
        if (funcionario == null) {
            JOptionPane.showMessageDialog(null, "CPF inválido!");
        } else {
            
            atualizarTabela(Arrays.asList(funcionario));
        }
    }
    
    public void atualizarTabela() {
        FuncionarioDAO repositorio = new FuncionarioDAO();
        List<Funcionario> lista = repositorio.listarTodos();
        this.modelo = new FuncionarioModeloTabela(lista);
        this.view.getTabelaFuncionario().setModel(modelo);
        
    }
    
    public void atualizarTabela(List<Funcionario> funcionario) {
        FuncionarioDAO repositorio = new FuncionarioDAO();
        this.modelo = new FuncionarioModeloTabela(funcionario);
        this.view.getTabelaFuncionario().setModel(modelo);
        
    }
    
    public void btnCancelar() {
        atualizarTabela();
    }
    
}
