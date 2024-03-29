package br.com.clinica.controller;

import br.com.clinica.model.dao.PacienteDAO;
import br.com.clinica.model.tabela.PacienteModeloTabela;
import br.com.clinica.model.objetos.Cidade;
import br.com.clinica.model.objetos.Endereco;
import br.com.clinica.model.objetos.Paciente;
import br.com.clinica.model.util.validacoes.PacienteValidar;
import br.com.clinica.view.telas.TelaPaciente;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class PacienteController {

    private PacienteValidar validarPaciente;
    private PacienteDAO repositorio;
    public PacienteModeloTabela modelo;
    private TelaPaciente view;

    public PacienteController(TelaPaciente view) {
        this.view = view;

    }

    public void salvar() {

        try {
            Paciente paciente = new Paciente();
            Endereco endereco = new Endereco();

            this.validarPaciente = new PacienteValidar();

            paciente.setNome(this.view.getTfNome().getText());
            String dataCampo = this.view.getTfDatadeNascimento().getText();
            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
            Date novaData = data.parse(dataCampo);
            paciente.setDataNascimento(novaData);
            paciente.setCpf(this.view.getTfCpf().getText());
            if (this.view.getrFeminino().isSelected()) {
                paciente.setSexo(this.view.getrFeminino().getText());
            } else {
                paciente.setSexo(this.view.getrMasculino().getText());
            }

            endereco.setUf(this.view.getCbEstado().getSelectedItem().toString());
            endereco.setBairro(this.view.getTfBairro().getText());
            endereco.setCep(this.view.getTfCep().getText());
            endereco.setCidade(this.view.getCbCidade().getSelectedItem().toString());
            endereco.setNumero(Integer.parseInt(this.view.getTfNumero().getText()));
            paciente.setTelefone(this.view.getTfTelefone().getText());
            endereco.setRua(this.view.getTfRua().getText());
            paciente.setEndereco(endereco);

            validarPaciente.validarPaciente(paciente, "salvar");

            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    public void editar() {
        try {
            this.view.setFuncao("alterar");
            Paciente paciente = new Paciente();
            Endereco endereco = new Endereco();
            this.validarPaciente = new PacienteValidar();

            if (this.view.getTabelaPaciente().getSelectedRow() != -1) {
                int codigo = Integer.parseInt(String.valueOf(this.view.getTabelaPaciente().
                        getValueAt(this.view.getTabelaPaciente().getSelectedRow(), 0)));

                paciente.setId(codigo);

                paciente.setNome(this.view.getTfNome().getText());
                String dataCampo = this.view.getTfDatadeNascimento().getText();
                SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
                Date novaData = data.parse(dataCampo);
                paciente.setDataNascimento(novaData);
                paciente.setCpf(this.view.getTfCpf().getText());

                if (this.view.getrFeminino().isSelected()) {
                    paciente.setSexo(this.view.getrFeminino().getText());
                } else if (this.view.getrMasculino().isSelected()) {
                    paciente.setSexo(this.view.getrMasculino().getText());
                }

                endereco.setUf(this.view.getCbEstado().getSelectedItem().toString());
                endereco.setBairro(this.view.getTfBairro().getText());
                endereco.setCep(this.view.getTfCep().getText());
                endereco.setCidade(this.view.getCbCidade().getSelectedItem().toString());
                endereco.setNumero(Integer.parseInt(this.view.getTfNumero().getText()));
                paciente.setTelefone(this.view.getTfTelefone().getText());
                endereco.setRua(this.view.getTfRua().getText());
                paciente.setEndereco(endereco);
                validarPaciente.validarPaciente(paciente, "alterar");
            } else {
                JOptionPane.showMessageDialog(null, "Escolha um paciente");
            }

            int linha = this.view.getTabelaPaciente().getSelectedRow();

            modelo.setValueAt(this.view.getTfNome().getText(), linha, 1);
            
            String dataCampo = this.view.getTfDatadeNascimento().getText();
            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
            Date novaData = data.parse(dataCampo);
            
            modelo.setValueAt((novaData), linha, 2);
            modelo.setValueAt(this.view.getTfCpf().getText(), linha, 3);
            modelo.setValueAt(this.view.getrMasculino().isSelected()
                    ? this.view.getrMasculino().getText() : this.view.getrFeminino().getText(), linha, 4);
            modelo.setValueAt(this.view.getCbEstado().getSelectedItem(), linha, 5);
            modelo.setValueAt(this.view.getTfCep().getText(), linha, 6);
            modelo.setValueAt(this.view.getCbCidade().getSelectedItem(), linha, 6);
            modelo.setValueAt(this.view.getTfBairro().getText(), linha, 7);
            modelo.setValueAt(this.view.getTfRua().getText(), linha, 8);
            modelo.setValueAt(this.view.getTfNumero().getText(), linha, 9);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }

    }

    public void bloquearCampos(boolean bloquear) {
        this.view.getTfNome().setEnabled(!bloquear);
        this.view.getTfBairro().setEnabled(!bloquear);
        this.view.getrMasculino().setEnabled(!bloquear);
        this.view.getrFeminino().setEnabled(!bloquear);
        this.view.getTfCep().setEnabled(!bloquear);
        this.view.getTfCpf().setEnabled(!bloquear);
        this.view.getTfDatadeNascimento().setEnabled(!bloquear);
        this.view.getTfNumero().setEnabled(!bloquear);
        this.view.getTfRua().setEnabled(!bloquear);
        this.view.getCbCidade().setEnabled(!bloquear);
        this.view.getCbEstado().setEnabled(!bloquear);
        this.view.getTfTelefone().setEnabled(!bloquear);

    }

    public void limparCampos() {
        this.view.getTfNome().setText("");
        this.view.getTfBairro().setText("");
        this.view.getTfCep().setText("");
        this.view.getTfCpf().setText("");
        this.view.getTfDatadeNascimento().setText("");
        this.view.getTfNumero().setText("");
        this.view.getTfTelefone().setText("");
        this.view.getTfRua().setText("");
        this.view.getrFeminino().setSelected(false);
        this.view.getrMasculino().setSelected(false);
        this.view.getCbCidade().removeAllItems();
    }

    public void listarCidadesPorEstado() {

        PacienteDAO repositorio = new PacienteDAO();

        String uf = this.view.getCbEstado().getSelectedItem().toString();

        ArrayList<Cidade> cidades = repositorio.listarCidadesPorUF(uf);

        this.view.getCbCidade().removeAllItems();

        for (Cidade cidade : cidades) {

            this.view.getCbCidade().addItem(cidade.getCidade());
        }

    }

    public void btnAlterar() {

        try {
            this.view.setFuncao("alterar");

            PacienteDAO repositorio = new PacienteDAO();
            int linha = this.view.getTabelaPaciente().getSelectedRow();
            long codigo = Long.parseLong(String.valueOf(this.view.getTabelaPaciente().getValueAt(linha, 0)));
            Paciente pacienteEncontrado = repositorio.listarPorId(codigo);

            if (pacienteEncontrado == null) {
                bloquearCampos(true);
            } else {
                bloquearCampos(false);
            }

            this.view.getTfNome().setText(pacienteEncontrado.getNome());
            this.view.getTfCpf().setText(pacienteEncontrado.getCpf());
            this.view.getTfCpf().setEnabled(false);
            this.view.getTfDatadeNascimento().setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(pacienteEncontrado.getDataNascimento().getTime())));
            this.view.getTfCep().setText(pacienteEncontrado.getEndereco().getCep());
            this.view.getTfRua().setText(pacienteEncontrado.getEndereco().getRua());
            this.view.getTfBairro().setText(pacienteEncontrado.getEndereco().getBairro());
            this.view.getTfNumero().setText(String.valueOf(pacienteEncontrado.getEndereco().getNumero()));
            this.view.getTfTelefone().setText(pacienteEncontrado.getTelefone());
            this.view.getCbEstado().setSelectedItem(pacienteEncontrado.getEndereco().getUf());
            this.view.getCbCidade().setSelectedItem(pacienteEncontrado.getEndereco().getCidade());

            if (pacienteEncontrado.getSexo().equals("Masculino")) {
                this.view.getrMasculino().setSelected(true);
            }
            if (pacienteEncontrado.getSexo().equals("Feminino")) {
                this.view.getrFeminino().setSelected(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Escolha um paciente para editar!");

        }
    }

    public void btnExcluir() {

        PacienteDAO repositorio = new PacienteDAO();
        if (this.view.getTabelaPaciente().getSelectedRow() != - 1) {
            int linha = this.view.getTabelaPaciente().getSelectedRow();
            long codigo = Long.parseLong(String.valueOf(this.view.getTabelaPaciente().getValueAt(linha, 0)));

            Paciente paciente = repositorio.listarPorId(codigo);

            switch (JOptionPane.showConfirmDialog(null, " Tem certeza que deseja excluir ? ", "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {

                case 0:

                    repositorio.excluir(paciente);
                    atualizarTabela();

                    break;

                case 1:
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Escolha um paciente");
        }

    }

    public void btnPesquisar() {

        PacienteDAO repositorio = new PacienteDAO();

        String cpf = this.view.getTfPesquisarCpf().getText();

        Paciente paciente = repositorio.listarPorCPF(cpf);

        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "CPF inválido!");
        } else {
            atualizarTabela(Arrays.asList(paciente));
        }

    }

    public void btnCancelar() {
        atualizarTabela();
    }

    public void atualizarTabela() {
        this.repositorio = new PacienteDAO();
        List<Paciente> pacientes = repositorio.listarTodos();
        this.modelo = new PacienteModeloTabela(pacientes);
        this.view.getTabelaPaciente().setModel(modelo);
    }

    public void atualizarTabela(List<Paciente> pacientes) {
        this.modelo = new PacienteModeloTabela(pacientes);
        this.view.getTabelaPaciente().setModel(modelo);
    }

}
