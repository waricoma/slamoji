import subprocess
import c4d
from c4d import gui
# Welcome to the world of Python


# Script state in the menu or the command palette
# Return True or c4d.CMD_ENABLED to enable, False or 0 to disable
# Alternatively return c4d.CMD_ENABLED|c4d.CMD_VALUE to enable and check/mark
#def state():
#    return True

# Main function
def main():
    obj = c4d.BaseObject(c4d.Osphere)
    doc.InsertObject(obj)
    v = c4d.Vector(100,100,100)
    obj.SetAbsPos(v)
    obj2 = c4d.BaseObject(c4d.Osphere)
    doc.InsertObject(obj2)

    try:
        res = subprocess.check_output(['curl', 'https://nnn.ed.jp'])
    except:
        print "Error."
    print res
    gui.MessageDialog(res)

    c4d.EventAdd()

# Execute main()
if __name__=='__main__':
    main()