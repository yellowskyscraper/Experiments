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

#include <portinfo>

#include "StreamTranslator.h" // implementation of class methods

#include <stdint.h> // USES uint32_t
#include <iostream> // USES std::istream
#include <stdexcept> // USES std::runtime_error

// ----------------------------------------------------------------------
// Default constructor
eqsimio::StreamTranslator::StreamTranslator(void)
{ // constructor
  const char bytes[4] = { 0x10, 0x20, 0x30, 0x40 };
  const int* pValue = (int*) bytes;
  const uint32_t valueBig = 0x10203040;
  const uint32_t valueLittle = 0x40302010;
  if (valueBig == *pValue)
    _nativeEndian = BIGENDIAN;
  else if (valueLittle == *pValue)
    _nativeEndian = LITTLEENDIAN;
  else
    throw std::runtime_error("Could not determine endian type.");
  _streamEndian = _nativeEndian;
} // constructor

// ----------------------------------------------------------------------
// Default destructor
eqsimio::StreamTranslator::~StreamTranslator(void)
{ // destructor
} // destructor

// ----------------------------------------------------------------------
// Read bytes from file and change endianness as necessary.
void
eqsimio::StreamTranslator::translate(std::istream& fin,
				     char* pData,
				     const int numValues,
				     const int valueSize) const
{ // translate
  fin.read(pData, numValues*valueSize);
  if (valueSize > 1 && _nativeEndian != _streamEndian)
    for (int iValue=0; iValue < numValues; ++iValue) {
      const int nswaps = valueSize / 2;
      char* buf = pData + (iValue*valueSize);
      for (int i=0; i < nswaps; ++i) {
	char tmp = buf[i];
	buf[i] = buf[valueSize-1-i];
	buf[valueSize-1-i] = tmp;
      } // for
    } // for
} // translate

// version
// $Id: StreamTranslator.cc 2365 2006-01-29 22:49:01Z brad $

// End of file 
