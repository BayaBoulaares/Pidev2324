package edu.esprit.APIapploadfichier;


import edu.esprit.entities.Document;
import edu.esprit.services.ServiceDocument;
import edu.esprit.entities.Matiere;
import edu.esprit.services.SeviceMatiere;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/statistiques")
public class StatistiquesAPI {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Statistique> getStatistiques() {
        SeviceMatiere sm = new SeviceMatiere();
        List<Matiere> matieres = null; // Remplacez ceci par votre méthode pour obtenir toutes les matières
        try {
            matieres = sm.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServiceDocument ds=new ServiceDocument();
        return matieres.stream()
                .map(matiere -> new Statistique(matiere.getNommatiere(),ds.getDocumentCountPerMatiere(matiere)))
                .collect(Collectors.toList());
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Statistique> getStatistiquesAnnee(Matiere mat) {
        ServiceDocument ds = new ServiceDocument();
        List<Document> documents = null;

        try {
            documents = ds.getAllByMatiere(mat);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return documents.stream()
                .collect(Collectors.groupingBy(doc -> doc.getDate().toLocalDate()))
                .entrySet().stream()
                .map(entry -> new Statistique(entry.getKey().toString(), entry.getValue().size()))
                .collect(Collectors.toList());
    }

}

