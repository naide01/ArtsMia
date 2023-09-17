package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private Graph<ArtObject, DefaultWeightedEdge> grafo; 
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
		
		
		
	}
	public void creaGrafo() {
		
		//GRAFO SEMPLICE NON ORIENTATO, PESATO
		grafo = new SimpleWeightedGraph<> (DefaultWeightedEdge.class);
		
		//AGGIUNGO I VERTICI = tutti gli oggetti presenti nella tabella
		dao.listObjects(idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		//AGGIUNGO GLI ARCHI
		//Metodo 1)
		for(ArtObject a1 : this.grafo.vertexSet()) {
			for (ArtObject a2 : this.grafo.vertexSet()) {
				if (!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
					//chiedo al db se devo collegare a1 e a2
					int peso = dao.getPeso(a1, a2);
					if (peso > 0) {
						Graphs.addEdgeWithVertices(this.grafo, a1, a2,peso);
					}
				}
			}
		}
		System.out.println("Grafo correttamente creato!" + "\n"+ 
				"# vertici: " + this.grafo.vertexSet());
	}
	
	
	//modifica
}
