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

/** @file lib/DataReader.h
 *
 * @brief C++ DataReader object
 *
 * C++ object for reading EqSim data files.
 */

#if !defined(eqsimio_datareader_h)
#define eqsimio_datareader_h

namespace eqsimio {
  class DataReader;
  class StreamTranslator; // forward declaration
} // eqsimio

#include <stdint.h> // USES uint32_t
#include <fstream> // HASA std::ifstream

/// C++ object for reading EqSim output.
class eqsimio::DataReader {
  friend class TestDataReader;
  
 public :
  // PUBLIC METHODS /////////////////////////////////////////////////////
  
  /** Constructor.
   *
   * @param filename Name of file
   */
  DataReader(const char* filename);
  
  /// Destructor
  ~DataReader(void);
  
  /** Read file header.
   *
   * @param pMeshID Pointer to mesh ID
   * @param pSimID Pointer to data ID
   */
  void readHeader(int32_t* pMeshID,
		  int32_t* pSimID);

  /** Read data information.
   *
   * @param pNumTimeSteps Pointer to number of time steps
   * @param pTimeStep Pointer to time step
   * @param pNumNodes Pointer to number of nodes
   * @param pNumNodeVals Pointer to number of values per node
   * @param pNumSets Pointer to number of element sets
   * @param ppSetSizes Pointer to array of number of elements in each set
   * @param pNumElemVals Pointer to number of values per element
   */
  void readDataInfo(uint32_t* pNumTimeSteps,
		    float* pTimeStep,
		    uint32_t* pNumNodes,
		    uint32_t* pNumNodeVals,
		    uint32_t* pNumSets,
		    uint32_t** ppSetSizes,
		    uint32_t* pNumElemVals);

  /** Read time.
   *
   * @param pTime Pointer to time of time step
   */
  void readTime(float* pTime);

  /** Read node values.
   *
   * @param ppNodeVals Pointer to array of node values
   */
  void readNodeVals(float** ppNodeVals);

  /** Read element values.
   *
   * @param pppElemVals Pointer to array of element values
   */
  void readElemVals(float*** pppElemVals);
  
private :
  // PRIVATE METHODS ////////////////////////////////////////////////////
  
  DataReader(const DataReader& r); ///< Not implemented
  const DataReader& operator=(const DataReader& r); ///< Not implemented
  
private :
// PRIVATE MEMBERS ////////////////////////////////////////////////////
  
  std::ifstream _fin; ///< Input file stream
  StreamTranslator* _pTranslator; ///< Stream reader and endian translator
  
  uint32_t _sizeNodeVals; ///< Total # of node values per time step

  uint32_t _numSets; ///< Number of element sets

  /// Array of total # of element values per time step for each element set
  uint32_t* _pSizeElemVals;

}; // DataReader

#endif // eqsimio_datareader_h

// version
// $Id: DataReader.h 2365 2006-01-29 22:49:01Z brad $

// End of file
