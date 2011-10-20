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

/** @file lib/GeometryImporter.h
 *
 * @brief C++ GeometryImporter object
 *
 * C++ object for importing EqSim geometry files (PUBLIC INTERFACE).
 */

#if !defined(eqsimio_geometryimporter_h)
#define eqsimio_geometryimporter_h

namespace eqsimio {
  class GeometryImporter;
} // eqsimio

#include <stdint.h> // USES uint32_t
#include <string> // HASA std::string

/// C++ object for importer EqSim output. (PUBLIC INTERFACE)
class eqsimio::GeometryImporter {
  friend class TestGeometryImporter;

 public :
  // PUBLIC METHODS /////////////////////////////////////////////////////

  /// Default constructor
  GeometryImporter(void);

  /// Default destructor
  ~GeometryImporter(void);

  /** Set filename of geometry file.
   *
   * @param name Name of file
   */
  void filename(const char* name);

  /** Import geometry. Geometry is passed back through the
   * arguments. All arrays will be allocated inside the method (this
   * is why the arguments are pointers).
   *
   * @param pMeshID Pointer to mesh ID
   * @param pNumNodes Pointer to number of nodes
   * @param pNumCoords Pointer to number of coordinates per node
   * @param ppCoords Pointer to array of nodal coordinates
   * @param pNumSets Pointer to number of element sets
   * @param ppSetSizes Pointer to array of number of elements in each set
   * @param ppSetNames Pointer to array of names of each element set
   * @param pConnSize Pointer to number of nodes in element
   * @param pppConns Pointer to array of arrays of element connectivities.
   */
  void import(int32_t* pMeshID,
	      uint32_t* pNumNodes,
	      uint32_t* pNumCoords,
	      float** ppCoords,
	      uint32_t* pNumSets,
	      uint32_t** ppSetSizes,
	      std::string** ppSetNames,
	      uint32_t* pConnSize,
	      uint32_t*** pppConns) const;
  
private :
  // PRIVATE METHODS ////////////////////////////////////////////////////
  
  GeometryImporter(const GeometryImporter& i); ///< Not implemented
  const GeometryImporter& operator=(const GeometryImporter& i); ///< Not implemented
    
private :
  // PRIVATE MEMBERS ////////////////////////////////////////////////////

  std::string _filename; ///< Name of file

}; // GeometryImporter

#include "GeometryImporter.icc" // inline methods

#endif // eqsimio_geometryimporter_h

// version
// $Id: GeometryImporter.h 2365 2006-01-29 22:49:01Z brad $

// End of file
