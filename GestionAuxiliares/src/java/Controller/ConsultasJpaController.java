/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import DTO.AuxiliaresSeleccionadosDTO;
import Entities.Area;
import Entities.Solicitante;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author DavidMontoya
 */
public class ConsultasJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    PreparedStatement pst = null;
    
    public ConsultasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Metodo que realiza una consulta SQL para saber
     * seleccionados en las distintas convocatorias con sus distinas pruebas.
     * @return una lista de AuxiliaresSeleccionadosDTO para su facil acceso.
     */
    public List<AuxiliaresSeleccionadosDTO> getAuxiliaresSeleccionados(){
        try {
            List<AuxiliaresSeleccionadosDTO> asDTO = new ArrayList<AuxiliaresSeleccionadosDTO>();
            Connection c= jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, SEL.ESTADO\n" +
                        "FROM SELECCION_AUXILIARES SEL, AUXILIAR AUX, CONVOCATORIA CON, EVALUACION_AUXILIARES EVA\n" +
                        "WHERE AUX.AUXILIAR_ID= SEL.ID_AUXILIARES \n" +
                        "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID\n" +
                        "AND EVA.ID_CONVOCATORIA= CONVOCATORIA_ID";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                AuxiliaresSeleccionadosDTO a = new AuxiliaresSeleccionadosDTO(rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getString("CONVOCATORIA_ID"), rs.getString("EVALUACION_AUXILIARES_ID"), rs.getShort("ESTADO"));
                asDTO.add(a);
            }
            c.close();
            pst.close();
            return asDTO;
//        EntityManager em = getEntityManager();
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresSeleccionadosDTO(aux.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, SEL.ESTADO) " +
//                                "FROM SeleccionAuxiliares sel, Auxiliar aux, Convocatoria con, EvaluacionAuxiliares eva " +
//                                "WHERE aux.AUXILIAR_ID= SEL.ID_AUXILIARES " +
//                                "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID " +
//                                "AND EVA.ID_CONVOCATORIA=CON.CONVOCATORIA_ID");
//        String convocatoria = "005";
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresSeleccionadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, C.convocatoriaId, EVA.evaluacionAuxiliaresId, SEL.estado) FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva, Convocatoria c WHERE EVA.idConvocatoria = c.convocatoriaId AND AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares  AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresSeleccionadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, aux.apellidoAuxiliar, EVA.evaluacionAuxiliaresId, SEL.estado) "
//                + "FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva "
//                + "WHERE AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares "
//                + "AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        return q.getResultList();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
