/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TodoApp2;

import controller.ProjectController;
import java.sql.Connection;
import java.time.Clock;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Maicol
 */
public class Main {

    public static void Main(String[] args) {

        Connection c = ConnectionFactory.getConnection();

        ConnectionFactory.closeConnection(c);

        
        ProjectController projectController = new ProjectController();
        
        Project project = new Project();
        project.setName("Projeto teste");
        project.setDescription("description");
        projectController.save(project);
        
        //project.setName("Novo nome do projeto");
        //projectController.update(project);
        
        //List<Project> projects = projectController.getAll();
        //System.out.println("Total de projetos= "+projects.size());
    }
    
}
