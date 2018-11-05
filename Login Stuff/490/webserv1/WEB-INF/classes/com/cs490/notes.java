/* Hidden JUST FOR REVIEW FACADE
	// Changed from using dataAccess/Facade to just services




	public Ingredient[] getIngredients() throws NamingException, SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();

		// Execute the query
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient");
		ResultSet rs = stmt.executeQuery();

		// Build the array of Ingredient Objects
		Ingredient[] ingArray = new Ingredient[100];
		int count = 0;
		while (rs.next()) {
			int theId = rs.getInt("id");
			String theName = rs.getString("name");
			String theCategory = rs.getString("category");
			Ingredient ing = new Ingredient(theId, theName, theCategory);
			ingArray[count] = ing;
			count++;
		}

		if (count > 0) {
			ingArray = Arrays.copyOf(ingArray, count);
			return ingArray;

		} else {
			return null;
		}

	}

	public Ingredient[] getIngredientByName(String theName) throws SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
				Connection con = dao.getConnection();

				// Execute the query
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient WHERE name=?");
				stmt.setString(1, theName);
				ResultSet rs = stmt.executeQuery();

				// Build the array of Ingredient Objects
				Ingredient[] ingArray = new Ingredient[100];
				int count = 0;
				while (rs.next()) {
					int theId = rs.getInt("id");
					String theName2 = rs.getString("name");
					String theCategory = rs.getString("category");
					Ingredient ing = new Ingredient(theId, theName2, theCategory);
					ingArray[count] = ing;
					count++;
				}

				if (count > 0) {
					ingArray = Arrays.copyOf(ingArray, count);
					return ingArray;

				} else {
					return null;
				}
	}

	public Ingredient[] getIngredientById(int theId) throws SQLException, ClassNotFoundException {
		// Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();

		// Execute the query
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM ingredient WHERE id=?");
		stmt.setInt(1, theId);
		ResultSet rs = stmt.executeQuery();

		// Build the array of Ingredient Objects
		Ingredient[] ingArray = new Ingredient[100];
		int count = 0;
		while (rs.next()) {
			int theId2 = rs.getInt("id");
			String theName = rs.getString("name");
			String theCategory = rs.getString("category");
			Ingredient ing = new Ingredient(theId2, theName, theCategory);
			ingArray[count] = ing;
			count++;
		}

		if (count > 0) {
			ingArray = Arrays.copyOf(ingArray, count);
			return ingArray;

		} else {
			return null;
		}

	}

	public Ingredient[] createIngredient(String newName, String newCategory) throws SQLException {
	
		//Get the connection fomr the IngredientDataAccess singleton object
		Connection con = dao.getConnection();
		
		//Execute the query
		PreparedStatement createStmt = con.prepareStatement("INSERT INTO Ingredient (name, category) VALUES (?, ?);");
		createStmt.setString(1, newName);
		createStmt.setString(2, newCategory);
		
		int res = createStmt.executeUpdate();
		System.out.println("Result is: " + res);
			
		//If insert was successful retrieve the new ingredient to get id
		if(res==1) {
			System.out.println("Ingredient added successfully!");
			
			PreparedStatement retrieveStmt = con.prepareStatement("SELECT * FROM Ingredient WHERE name=? AND category=?;");
			retrieveStmt.setString(1, newName);
			retrieveStmt.setString(2, newCategory);

			ResultSet rs = retrieveStmt.executeQuery();
					
			System.out.println("\nRS: " + rs);
				
			int count = 0;
			int MAX = 100;
			Ingredient[] ingArray = new Ingredient[MAX];
			
			while (rs.next()) {
				int theId = rs.getInt("id");
				String ingName = rs.getString("name");
				String ingCategory = rs.getString("category");
				Ingredient ing = new Ingredient(theId, ingName, ingCategory);
				System.out.println(ing);
				ingArray[count] = ing;
				count++;
				System.out.println(ing);
			}
				
			ingArray = Arrays.copyOf(ingArray, count);
				
			return ingArray;
				  
				
			} // END IF
			else { //The ingredient was not successfully inserted
				 return null;
				  
			} // END ELSE
						
		}*/

	/**
	 * End database modification code
	 */
