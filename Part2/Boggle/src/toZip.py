#!/usr/bin/env python
import os
import zipfile

def zipdir(path, ziph):
    # ziph is zipfile handle    
    for root, dirs, files in os.walk(path):
        for file in files:
                names = ["BoggleSolver.java", "AllPaths.java", "Helper.java"]
                if (file in names):
                        ziph.write(os.path.join(root, file))

if __name__ == '__main__':
    zipf = zipfile.ZipFile('boggle-submit.zip', 'w', zipfile.ZIP_DEFLATED)
    zipdir('./', zipf)
    zipf.close()