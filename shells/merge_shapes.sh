echo "Merging DEM to GTiff"
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_merge.py -init 255 -of GTiff -o /Users/james/Desktop/OrientationResearch/GIS_USGS/xMerge/xtest.tif /Users/james/Desktop/OrientationResearch/GIS_USGS/xMerge/central_ca_mhw.asc /Users/james/Desktop/OrientationResearch/GIS_USGS/xMerge/srtm_12_05.asc
echo "Done Processing"