/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Maicol
 */
public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updatedAt)"
                + "VALUES (?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //conexão com banco de dados
            connection = ConnectionFactory.getConnection();
            //cria um preparedStatement, classe usada pra executar a query
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            //statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar projeto", ex);

        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE tasks SET "
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {

            //cria uma conexão com o banco
            connection = ConnectionFactory.getConnection();

            //cria um preparedstatement, classe usada para preparar a query
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getUpdatedAt().getTime()));
            statement.setDate(4, new Date(project.getCreatedAt().getTime()));
            statement.setInt(5, project.getId());

            //executa a sql para inserção dos dados
            statement.execute();
        } catch (SQLException ex) {

            throw new RuntimeException("Erro ao salvar projeto", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Project> getAll() {
        //MINDE PAPAE

//metodo para buscar tarefas 
//lista de tarefas
        String sql = "SELECT * FROM projects";

        //LISTA DE TAREFAS (tipo vetor) para coleções com conjunto de valores
        //que vai ser devolvida quando a chamada do método acontecer
        //estrutura de lista
        //List<Task> tasks = new ArrayList<Task>();
        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        connection = ConnectionFactory.getConnection();
        statement = connection.prepareStatement(sql);
        //statement.setInt(1, idProject);
        //statement.executeQuery();
        //esse metodo query me devolve o valor de resposta q rolou no banco de dados

        resultSet = null;

        try {

            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));

                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));

                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                //colocar as taretas dentro da lista de taretas
                projects.add(project);

            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
            //ConnectionFactory.closeConnection(connection, statement, resultset);
        }

        //lista de tarefas que foi criada e carregada do banco de dados
        return projects;
    }

        public void removeById(int idProject) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //estabelecer uma conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
           
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //setando os calores
            statement.setInt(1, idProject);
            //substitui a id do where
            
            //executando a query
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }
    
}
