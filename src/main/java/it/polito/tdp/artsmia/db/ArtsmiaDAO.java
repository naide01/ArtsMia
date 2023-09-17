package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {
	
	//IN QUESTO MODO AGGIUNGO TUTTO UNA SOLA VOLTA

	public void listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!idMap.containsKey(res.getInt("object_id"))){
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
					idMap.put(artObj.getId(), artObj);
				}
			}
			conn.close();

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//METODO CHE MI DA IL PESO
	public int getPeso(ArtObject a1, ArtObject a2) {
		String sql= "SELECT COUNT(*) AS peso "
				+ "FROM exhibition_objects e1, exhibition_objects e2 "
				+ "WHERE e1.exhibition_id = e2.exhibition_id AND  "
				+ "e1.object_id = ?  AND e2.object_id = ? ";
		
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a1.getId());
			st.setInt(2, a2.getId());
			ResultSet res = st.executeQuery();
			int peso = 0 ;
			
			if (res.next()) {
				peso = res.getInt("peso");
			}
			conn.close();
			return peso;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
			
		}
	}
	
	//modifica
}
