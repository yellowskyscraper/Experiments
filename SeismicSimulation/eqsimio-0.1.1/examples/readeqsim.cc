// -*- C++ -*-
//
// ======================================================================
//
//                           Brad T. Aagaard
//                        U.S. Geological Survey
//
// {LicenseText}
//
// ======================================================================
//

/* Example demonstrating using of GeometryImporter and DataImporter to
 * read EqSim geometry and data files.
 */

#include <portinfo>

#include "GeometryImporter.h"
#include "DataImporter.h"

#include <stdlib.h> // USES exit()
#include <unistd.h> // USES getopt()
#include <string> // USES std::string
#include <iostream> // USES std::cerr, std::cout
#include <iomanip> // USES std::setw(), std::setprecision()
#include <stdexcept> // USES std::exception
#include <assert.h> // USES assert()

// ----------------------------------------------------------------------
// Print usage to standard error and exit.
void
showUsage(void)
{ // showUsage
  std::cerr
    << "usage: readeqsim [-A] -g geometryin -d datain\n"
    "  -g geometryin     EqSim geometry file\n"
    "  -d datain         EqSim data file\n"
    "  -A                Display complete information\n"
    "\n";
} // showUsage

// ---------------------------------------------------------------------------
// Parse command line arguments
void
parseArgs(int& argc,
	  char** argv,
	  std::string* pFilenameGeom,
	  std::string* pFilenameData,
	  bool* pDisplayAll)
{ // parseArgs
  assert(0 != pFilenameGeom);
  assert(0 != pFilenameData);
  assert(0 != pDisplayAll);
  extern char* optarg;

  int nparsed = 1;
  *pFilenameGeom = "";
  *pFilenameData = "";
  *pDisplayAll = false;
  int c = EOF;
  while ( (c = getopt(argc, argv, "Ad:g:") ) != EOF) {
    switch (c)
      { // switch
      case 'A' : // process -A option
	*pDisplayAll = true;
	nparsed += 1;
	break;
      case 'd' : // process -d option
	*pFilenameData = optarg;
	nparsed += 2;
	break;
      case 'g' : // process -g option
	*pFilenameGeom = optarg;
	nparsed += 2;
	break;
      default :
	showUsage();
	exit(1);
      } // switch
  } // while
  if (nparsed != argc ||
      0 == pFilenameGeom->length() ||
      0 == pFilenameData->length()) {
    showUsage();
    exit(1);
  } // if
} // parseArgs

// ----------------------------------------------------------------------
// Dump geometry information to stdout
void
displayGeometry(const int32_t meshID,
		const uint32_t numNodes,
		const uint32_t numCoords,
		const float* pCoords,
		const uint32_t numSets,
		const uint32_t* pSetSizes,
		const std::string* pSetNames,
		const uint32_t connSize,
		uint32_t** const ppConns,
		const bool displayAll)
{ // displayGeometry
  const int maxDisplay = 20;

  std::cout
    << "Mesh ID: " << meshID << "\n"
    << "# nodes: " << numNodes << "\n"
    << "# coordinates: " << numCoords << "\n"
    << "\n"
    << "Coordinates of nodes:\n";
  const bool showAll = maxDisplay >= numNodes || displayAll;
  const int size1 = showAll ? numNodes : maxDisplay / 2;
  const int size2 = showAll ? 0 : size1;

  if (!showAll)
    std::cout << "  (first and last " << size1 << " nodes)\n";

  std::cout
    << std::resetiosflags(std::ios::fixed)
    << std::setiosflags(std::ios::scientific)
    << std::setprecision(4);

  for (int i=0; i < size1; ++i)
    std::cout
      << "  "
      << std::setw(12) << pCoords[numCoords*i  ]
      << std::setw(12) << pCoords[numCoords*i+1]
      << std::setw(12) << pCoords[numCoords*i+2]
      << "\n";
  if (!showAll)
    std::cout << "  ...\n";
  for (int i=numNodes-size2; i < numNodes; ++i)
    std::cout
      << "  "
      << std::setw(12) << pCoords[numCoords*i  ]
      << std::setw(12) << pCoords[numCoords*i+1]
      << std::setw(12) << pCoords[numCoords*i+2]
      << "\n";
  
  std::cout
    << "\n"
    << "# element sets: " << numSets << "\n"
    << "Element sets:\n";
  for (int iSet=0; iSet < numSets; ++iSet) {
    std::cout
      << "  Name: '" << pSetNames[iSet] << "'\n"
      << "  # elements: " << pSetSizes[iSet] << "\n"
      << "  # nodes per element: " << connSize << "\n";
    
    const bool showAll = maxDisplay >= pSetSizes[iSet] || displayAll;
    const int size1 = showAll ? pSetSizes[iSet] : maxDisplay / 2;
    const int size2 = showAll ? 0 : size1;
    if (!showAll)
      std::cout << "  (first and last " << size1 << " elements)\n";
    
    for (int i=0; i < size1; ++i) {
      std::cout << "    ";
      for (int iConn=0; iConn < connSize; ++iConn)
	std::cout << std::setw(8) << ppConns[iSet][connSize*i+iConn];
      std::cout << "\n";
    } // for
    if (!showAll)
      std::cout << "  ...\n";
    for (int i=pSetSizes[iSet]-size2; i < pSetSizes[iSet]; ++i) {
      std::cout << "    ";
      for (int iConn=0; iConn < connSize; ++iConn)
	std::cout << std::setw(8) << ppConns[iSet][connSize*i+iConn];
      std::cout << "\n";
    } // for
  } // for
} // displayGeometry

// ----------------------------------------------------------------------
// Dump data information to stdout
void
displayData(const int32_t meshID,
	    const int32_t simID,
	    const uint32_t numTimeSteps,
	    const float timeStep,
	    const uint32_t numNodes,
	    const uint32_t numNodeVals,
	    const uint32_t numSets,
	    const uint32_t* pSetSizes,
	    const uint32_t numElemVals)
{ // displayData
  const int maxDisplay = 20;

  std::cout
    << "Mesh ID: " << meshID << "\n"
    << "Simulation ID: " << simID << "\n"
    << "# time steps: " << numTimeSteps << "\n"
    << "# nodes: " << numNodes << "\n"
    << "# values per node: " << numNodeVals << "\n"
    << "# element sets: " << numSets << "\n"
    << "# elements per set:";
  for (int i=0; i < numSets; ++i)
    std::cout << " " << pSetSizes[i];
  std::cout
    << "\n"
    << "# values per element: " << numElemVals << "\n"
    << "\n";
} // displayData

// ----------------------------------------------------------------------
// Dump time step information to stdout
void
displayData(const float timeVal,
	    const float* pNodeVals,
	    const uint32_t numNodes,
	    const uint32_t numNodeVals,
	    float** const ppElemVals,
	    const uint32_t numSets,
	    const uint32_t* pSetSizes,
	    const uint32_t numElemVals,
	    const bool displayAll)
{ // displayData
  const int maxDisplay = 20;

  std::cout
    << std::resetiosflags(std::ios::fixed)
    << std::setiosflags(std::ios::scientific)
    << std::setprecision(4);

  std::cout
    << "Time: " << std::setw(12) << timeVal << "\n"
    << "Node values:\n";

  const bool showAll = maxDisplay >= numNodes || displayAll;
  const int size1 = showAll ? numNodes : maxDisplay / 2;
  const int size2 = showAll ? 0 : size1;

  if (numNodeVals > 0) {
    if (!showAll)
      std::cout << "  (first and last " << size1 << " nodes)\n";
    
    for (int i=0; i < size1; ++i) {
      std::cout
	<< "  ";
      for (int iVal=0; iVal < numNodeVals; ++iVal)
	std::cout << std::setw(12) << pNodeVals[numNodeVals*i+iVal];
      std::cout << "\n";
    } // for
    if (!showAll)
      std::cout << "  ...\n";
    for (int i=numNodes-size2; i < numNodes; ++i) {
      std::cout
	<< "  ";
      for (int iVal=0; iVal < numNodeVals; ++iVal)
	std::cout << std::setw(12) << pNodeVals[numNodeVals*i+iVal];
      std::cout << "\n";
    } // for
  } // if
  
  std::cout
    << "Element values:\n";
  if (numElemVals > 0) {
    for (int iSet=0; iSet < numSets; ++iSet) {
      std::cout << "  Set: " << iSet << "\n";
      
      const bool showAll = maxDisplay >= pSetSizes[iSet] || displayAll;
      const int size1 = showAll ? pSetSizes[iSet] : maxDisplay / 2;
      const int size2 = showAll ? 0 : size1;
      if (!showAll)
	std::cout << "  (first and last " << size1 << " elements)\n";
      
      for (int i=0; i < size1; ++i) {
	std::cout << "    ";
	for (int iVal=0; iVal < numElemVals; ++iVal)
	  std::cout << std::setw(12) << ppElemVals[iSet][numElemVals*i+iVal];
	std::cout << "\n";
      } // for
      if (!showAll)
	std::cout << "  ...\n";
      for (int i=pSetSizes[iSet]-size2; i < pSetSizes[iSet]; ++i) {
	std::cout << "    ";
	for (int iVal=0; iVal < numElemVals; ++iVal)
	  std::cout << std::setw(12) << ppElemVals[iSet][numElemVals*i+iVal];
	std::cout << "\n";
      } // for
    } // for
  } // if
} // displayData

// ----------------------------------------------------------------------
int
main(int argc,
     char* argv[])
{ // main

  try {
    std::string filenameGeom = "";
    std::string filenameData = "";
    bool displayAll = 0;
    
    parseArgs(argc, argv, &filenameGeom, &filenameData, &displayAll);

    // Read geometry file -----------------------------------------------

    // Create geometry object to read geometry
    eqsimio::GeometryImporter geometry;

    // Set filename
    geometry.filename(filenameGeom.c_str());

    // Create variables to hold geometry information
    int32_t meshID = 0;
    uint32_t numNodes = 0;
    uint32_t numCoords = 0;
    float* pCoords = 0;
    uint32_t numSets = 0;
    uint32_t* pSetSizes = 0;
    std::string* pSetNames = 0;
    uint32_t connSize = 0;
    uint32_t** ppConns = 0;

    // Read geometry
    geometry.import(&meshID, 
		    &numNodes, &numCoords, &pCoords,
		    &numSets, &pSetSizes, &pSetNames, &connSize, &ppConns);

    // Dump geometry information to stdout
    displayGeometry(meshID, 
		    numNodes, numCoords, pCoords,
		    numSets, pSetSizes, pSetNames, connSize, ppConns,
		    displayAll);

    // Cleanup
    delete[] pCoords; pCoords = 0;
    delete[] pSetSizes; pSetSizes = 0;
    delete[] pSetNames; pSetNames = 0;
    for (int i=0; i < numSets; ++i) {
      delete[] ppConns[i]; ppConns[i] = 0;
    } // for
    delete[] ppConns; ppConns = 0;

    std::cout << "-----------------------------------"
	      << "-----------------------------------\n\n";
    
    // Read data file ---------------------------------------------------    

    // Create data object to read data file
    eqsimio::DataImporter data;

    // Set filename of data file
    data.filename(filenameData.c_str());
    
    // Create variables to hold data information
    int32_t meshIDD = 0;
    int32_t simID = 0;
    uint32_t numTimeSteps = 0;
    float timeStep = 0.0;
    uint32_t numNodesD = 0;
    uint32_t numNodeVals = 0;
    uint32_t numSetsD = 0;
    uint32_t* pSetSizesD = 0;
    uint32_t numElemVals = 0;

    float time = 0;
    float* pNodeVals = 0;
    float** ppElemVals = 0;

    // Open data file
    data.open(&meshIDD, &simID,
	      &numTimeSteps, &timeStep,
	      &numNodesD, &numNodeVals,
	      &numSetsD, &pSetSizesD, &numElemVals);

    // Dump data information to stdout
    displayData(meshIDD, simID,
		numTimeSteps, timeStep,
		numNodesD, numNodeVals,
		numSetsD, pSetSizesD, numElemVals);

    // Loop over time steps
    std::cout << "Displaying first and last time steps.\n\n";
    for (int i=0; i < numTimeSteps; ++i) {
      // Read time step from data file
      data.readTimeStep(&time, &pNodeVals, &ppElemVals);
      if (displayAll || i == 0 || (i > 0 && i == numTimeSteps-1))
	// Dump time step data to stdout
	displayData(time, 
		    pNodeVals, numNodesD, numNodeVals,
		    ppElemVals, numSetsD, pSetSizesD, numElemVals,
		    displayAll);
    } // for

    // Cleanup
    delete[] pSetSizes; pSetSizes = 0;
    delete[] pNodeVals; pNodeVals = 0;
    for (int i=0; i < numSetsD; ++i) {
      delete[] ppElemVals[i]; ppElemVals[i] = 0;
    } // for
    delete[] ppElemVals; ppElemVals = 0;

  } catch (const std::exception& err) {
    std::cerr << "Trapped fatal error.\n" << err.what() << std::endl;
    return 1;
  } catch (...) {
    std::cerr << "Trapped unknown fatal error." << std::endl;
    return 1;
  } // catch

  return 0;
} // main
