/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author Maicol
 */
public class TaskController {

    public void save(Task task) {
        //salvar no banco de dados

        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            //sempre que abrir a conexão e o statemente, tem que fechar os 2
            statement.execute();
            //executa o sql para inserção dos dados
            //stmt.execute();
        } catch (Exception ex) {
            //tratamento de error
            throw new RuntimeException("Erro ao salvar tarefa" + ex.getMessage(), ex);
        } finally {
            //fechar conexão
            ConnectionFactory.closeConnection(connection, statement);
            //ConnectionFactory.closeConnection(connetion);

        }
    }

    public void update(Task task) {
        //atualizar
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadeline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //estabelecendo a conexão
            connection = ConnectionFactory.getConnection();
            
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //executando a query
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);

        }

    }

    public void removeById(int taskId) throws SQLException {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //estabelecer uma conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
           
            //preparando a query
            statement = connection.prepareStatement(sql);
            
            //setando os calores
            statement.setInt(1, taskId);
            //substitui a id do where
            
            //executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public List<Task> getAll(int idProject) {
        //MINDE PAPAE

//metodo para buscar tarefas 
//lista de tarefas
        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        
        Connection connection = null;
        PreparedStatement statement = null;
        
        ResultSet resultSet = null;

        //LISTA DE TAREFAS (tipo vetor) para coleções com conjunto de valores
        //que vai ser devolvida quando a chamada do método acontecer
        //estrutura de lista
        //List<Task> tasks = new ArrayList<Task>();
  
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            //statement.executeQuery();
            //esse metodo query me devolve o valor de resposta q rolou no banco de dados

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));

                //colocar as taretas dentro da lista de taretas
                tasks.add(task);

            }

        } catch (Exception ex) {
 throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
            //ConnectionFactory.closeConnection(connection, statement, resultset);
        }

        //lista de tarefas que foi criada e carregada do banco de dados
        return tasks;
    }
}
