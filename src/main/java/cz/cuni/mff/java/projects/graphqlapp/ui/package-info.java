/**
 * <p>
 *     Manages the GUI application which provides a user-friendly graphical tool for viewing
 *     population data about the administrative regions.
 * </p>
 * <p>
 *     Holds an instance of the GraphQL provider to query for data using build GraphQL queries.
 *     Could be extended to access the endpoint remotely.
 * </p>
 * <p>
 *     Written using Swing. Mainly split into the left <b>AreaPanel</b> and right <b>PopulationPanel</b>.
 *     The <b>AreaPanel</b> allows the user to filter available areas and select which to display.
 *     The <b>PopulationPanel</b> allows the selection of fields to be queried for, as well as the
 *     PopulationCards, which display the query results, one Card per area.
 * </p>
 */
package cz.cuni.mff.java.projects.graphqlapp.ui;