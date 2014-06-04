/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import DTO.AuxiliaresPresentadosDTO;
import DTO.EvaluacionesDTO;
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
     * los auxiliares presentados a las convocatorias con sus distintas pruebas.
     * @return una lista de AuxiliaresPresentadosDTO para su facil acceso.
     */
    public List<AuxiliaresPresentadosDTO> getAuxiliaresPresentados(){
        try {
            List<AuxiliaresPresentadosDTO> asDTO = new ArrayList<AuxiliaresPresentadosDTO>();
            Connection c= jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, CON.FECHA_CREACION , AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, SEL.ESTADO " +
                            "FROM SELECCION_AUXILIARES SEL, AUXILIAR AUX, CONVOCATORIA CON, EVALUACION_AUXILIARES EVA " +
                            "WHERE AUX.AUXILIAR_ID= SEL.ID_AUXILIARES " +
                            "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID " +
                            "AND EVA.ID_CONVOCATORIA=CON.CONVOCATORIA_ID " +
                            "ORDER BY CON.CONVOCATORIA_ID ASC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                AuxiliaresPresentadosDTO a = new AuxiliaresPresentadosDTO(rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getString("CONVOCATORIA_ID"), rs.getString("EVALUACION_AUXILIARES_ID"), rs.getShort("ESTADO"), rs.getDate("FECHA_CREACION"));
                asDTO.add(a);
            }
            pst.close();
            return asDTO;
//        EntityManager em = getEntityManager();
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(aux.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, SEL.ESTADO) " +
//                                "FROM SeleccionAuxiliares sel, Auxiliar aux, Convocatoria con, EvaluacionAuxiliares eva " +
//                                "WHERE aux.AUXILIAR_ID= SEL.ID_AUXILIARES " +
//                                "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID " +
//                                "AND EVA.ID_CONVOCATORIA=CON.CONVOCATORIA_ID");
//        String convocatoria = "005";
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, C.convocatoriaId, EVA.evaluacionAuxiliaresId, SEL.estado) FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva, Convocatoria c WHERE EVA.idConvocatoria = c.convocatoriaId AND AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares  AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, aux.apellidoAuxiliar, EVA.evaluacionAuxiliaresId, SEL.estado) "
//                + "FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva "
//                + "WHERE AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares "
//                + "AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        return q.getResultList();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<EvaluacionesDTO> getPromedioEvaluaciones(){
        try {
            List<EvaluacionesDTO> peDTO = new ArrayList<EvaluacionesDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT ROUND(AVG(SEL.CALIFICACION)) as PROMEDIO, EVA.EVALUACION_AUXILIARES_ID " +
                        "FROM AUXILIAR AUX, SELECCION_AUXILIARES SEL, EVALUACION_AUXILIARES EVA " +
                        "WHERE SEL.ID_AUXILIARES= AUX.AUXILIAR_ID " +
                        "AND SEL.ESTADO = 1 " +
                        "GROUP BY EVA.EVALUACION_AUXILIARES_ID";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                EvaluacionesDTO p = new EvaluacionesDTO(rs.getString(2) ,rs.getShort(1));
                peDTO.add(p);
            }
            pst.close();
            return peDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<EvaluacionesDTO> getAuxiliaresSeleccionados(){
        try {
            List<EvaluacionesDTO> peDTO = new ArrayList<EvaluacionesDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, SEL.ID_EVALUACION_AUXILIARES, SEL.CALIFICACION, SEL.PROMEDIO " +
                        "FROM AUXILIAR AUX, SELECCION_AUXILIARES SEL " +
                        "WHERE SEL.ID_AUXILIARES=AUX.AUXILIAR_ID " +
                        "AND SEL.ESTADO=1 " +
                        "ORDER BY SEL.PROMEDIO DESC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                EvaluacionesDTO p = new EvaluacionesDTO(rs.getString("ID_EVALUACION_AUXILIARES"), rs.getDouble("PROMEDIO"), rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getDouble("CALIFICACION"));
                peDTO.add(p);
            }
            pst.close();
            return peDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
