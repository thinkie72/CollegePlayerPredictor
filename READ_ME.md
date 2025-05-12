**College Player Predictor**

Created by Tyler Hinkie in April and May 2025 for Adventures in Algorithms at the Menlo School

**Overview:**

NBA Player Predictor is a Java-based desktop application that uses real NCAA and NBA player data to estimate the professional potential of college basketball players based on their physical attributes and college statistics.

The program takes in user input (name, height, weight, PPG, RPG, APG, and whether they played for a "blue blood" school) and outputs:

- A list of **5 similar historical players**

- **Predicted NBA statistics** (PPG, RPG, APG)

- **An assigned player role** (e.g., Role Player, Starter, ALL-STAR, MVP Candidate)

**Key Features:**
- **Interactive GUI:** Built using Java Swing, allowing users to easily enter player data and view predictions.

- **Data-Driven Analysis:** Compares input to real historical data from NCAA and NBA player records.

- **Similarity Algorithm:** Uses Euclidean distance on scaled stats (weighted RPG and APG) to find similar players.

- **Smart Role Prediction:** Applies logic based on modern NBA stat thresholds to categorize players.