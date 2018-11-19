#!/usr/bin/env python
import os
import zipfile

def zipdir(path, ziph):
    # ziph is zipfile handle    
    for root, dirs, files in os.walk(path):
        for file in files:
                names = ["WordNet.java", "Outcast.java", "SAP.java"]
                if (file in names):
                        ziph.write(os.path.join(root, file))

if __name__ == '__main__':
    zipf = zipfile.ZipFile('wordnet.zip', 'w', zipfile.ZIP_DEFLATED)
    zipdir('./', zipf)
    zipf.close()